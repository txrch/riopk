package com.app.stats;

import com.app.taxiqos.TaxiServiceQualityAssessment;
import com.app.taxiqos.TaxiServiceQualityAssessmentService;
import com.app.system.Result;
import com.app.system.StatusCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

import static com.app.util.Global.ADMIN;

@RestController
@RequestMapping("/stats")
@RequiredArgsConstructor
@Secured({ADMIN})
public class StatsController {

    private final TaxiServiceQualityAssessmentService taxiServiceQualityAssessmentService;

    // Статистика по рейтингу качества обслуживания
    @GetMapping("/rating")
    public Result rating() {
        Map<String, List<?>> res = new HashMap<>();

        List<String> names = new ArrayList<>();
        List<Float> values = new ArrayList<>();

        // Получаем все оценки качества обслуживания такси, отсортированные по рейтингу
        List<TaxiServiceQualityAssessment> assessments = taxiServiceQualityAssessmentService.findAll(Sort.by(Sort.Direction.DESC, "rating"));

        for (int i = 0; i < assessments.size(); i++) {
            if (i == 5) break;
            names.add(assessments.get(i).getTaxiServiceName());
            values.add(assessments.get(i).getRating());
        }

        res.put("names", names);
        res.put("values", values);

        return new Result(
                true,
                StatusCode.SUCCESS,
                "Success Stats Rating",
                Collections.unmodifiableMap(res)
        );
    }

    // Статистика по времени ожидания такси
    @GetMapping("/waiting-time")
    public Result waitingTime() {
        Map<String, List<?>> res = new HashMap<>();

        List<String> names = new ArrayList<>();
        List<Integer> values = new ArrayList<>();

        // Получаем все оценки, отсортированные по времени ожидания
        List<TaxiServiceQualityAssessment> assessments = taxiServiceQualityAssessmentService.findAll(Sort.by(Sort.Direction.ASC, "waitingTime"));

        for (int i = 0; i < assessments.size(); i++) {
            if (i == 5) break;
            names.add(assessments.get(i).getTaxiServiceName());
            values.add(assessments.get(i).getWaitingTime());
        }

        res.put("names", names);
        res.put("values", values);

        return new Result(
                true,
                StatusCode.SUCCESS,
                "Success Stats Waiting Time",
                Collections.unmodifiableMap(res)
        );
    }

    // Статистика по состоянию автомобиля
    @GetMapping("/car-condition")
    public Result carCondition() {
        Map<String, List<?>> res = new HashMap<>();

        List<String> names = new ArrayList<>();
        List<String> values = new ArrayList<>();

        // Получаем все оценки, отсортированные по состоянию автомобиля
        List<TaxiServiceQualityAssessment> assessments = taxiServiceQualityAssessmentService.findAll(Sort.by(Sort.Direction.DESC, "carCondition"));

        for (int i = 0; i < assessments.size(); i++) {
            if (i == 5) break;
            names.add(assessments.get(i).getTaxiServiceName());
            values.add(assessments.get(i).getCarCondition());
        }

        res.put("names", names);
        res.put("values", values);

        return new Result(
                true,
                StatusCode.SUCCESS,
                "Success Stats Car Condition",
                Collections.unmodifiableMap(res)
        );
    }
}
