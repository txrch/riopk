package com.app.taxiqos;

import com.app.appUser.UserService;
import com.app.system.exception.BadRequestException;
import com.app.system.exception.ObjectNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

import static com.app.util.Global.saveFile;

@Service
@RequiredArgsConstructor
public class TaxiServiceQualityPolicyService {

    private final TaxiServiceQualityPolicyRepository repository;
    private final RiskAnalysisService riskAnalysisService;
    private final UserService userService;

    public TaxiServiceQualityPolicy createBasicPolicy() {
        TaxiServiceQualityPolicyBuilder builder = new ConcreteTaxiServiceQualityPolicyBuilder();
        TaxiServiceQualityPolicyDirector director = new TaxiServiceQualityPolicyDirector();
        director.constructBasicPolicy(builder);
        TaxiServiceQualityPolicy policy = builder.build();
        return repository.save(policy);
    }

    public List<TaxiServiceQualityPolicy> findAll(Sort sort) {
        return repository.findAll(sort);
    }

    public List<TaxiServiceQualityPolicy> myPolicies() {
        return userService.getCurrentUser().getTaxiServiceQualityPolicies();
    }

    public TaxiServiceQualityPolicy find(String id) {
        TaxiServiceQualityPolicy policy = repository.findById(Long.parseLong(id))
                .orElseThrow(() -> new ObjectNotFoundException("Не найдено политики по ИД: " + id));
        policy.setViewCount(policy.getViewCount() + 1);
        return repository.save(policy);
    }

    public TaxiServiceQualityPolicy approve(String id) {
        TaxiServiceQualityPolicy policy = find(id);
        policy.setStatus(TaxiServiceQualityStatus.APPROVED);
        return repository.save(policy);
    }

    public TaxiServiceQualityPolicy save(TaxiServiceQualityPolicy save, String orderingId) {
        save.setRiskAnalysis(riskAnalysisService.find(orderingId));
        save.setOwner(userService.getCurrentUser());
        return repository.save(save);
    }

    public TaxiServiceQualityPolicy update(String id, TaxiServiceQualityPolicy update, String orderingId) {
        TaxiServiceQualityPolicy old = find(id);
        old.update(update);
        old.setRiskAnalysis(riskAnalysisService.find(orderingId));
        return repository.save(old);
    }

    public TaxiServiceQualityPolicy updateFile(String id, MultipartFile file) {
        TaxiServiceQualityPolicy policy = find(id);
        try {
            policy.setFile(saveFile(file, "taxi_quality_policy"));
        } catch (IOException e) {
            if (policy.getFile().isEmpty()) repository.deleteById(policy.getId());
            throw new BadRequestException("Некорректный файл");
        }
        return repository.save(policy);
    }

    public void delete(String id) {
        repository.deleteById(find(id).getId());
    }
}