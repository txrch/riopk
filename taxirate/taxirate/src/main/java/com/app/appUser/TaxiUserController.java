package com.app.appUser;

import com.app.appUser.profile.converter.TaxiUserToUserDtoConverter;
import com.app.system.Result;
import com.app.system.StatusCode;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

import static com.app.util.Global.*;

@RestController
@RequestMapping("/taxi-users")
@RequiredArgsConstructor
public class TaxiUserController {

    private final TaxiUserService service;
    private final TaxiUserToUserDtoConverter toDtoConverter;
    private final TaxiUserDtoToUserConverter toUserConverter;

    @Secured({ADMIN})
    @GetMapping("/all")
    public Result findAll() {
        return new Result(
                true,
                StatusCode.SUCCESS,
                "Find All Taxi Users Success",
                service.findAll().stream().map(toDtoConverter::convert).collect(Collectors.toList())
        );
    }

    @Secured({ADMIN, CLIENT})
    @GetMapping
    public Result find() {
        return new Result(
                true,
                StatusCode.SUCCESS,
                "Find Taxi User Success",
                toDtoConverter.convert(service.getCurrentUser())
        );
    }

    @Secured({ADMIN})
    @GetMapping("/{userId}")
    public Result findById(@PathVariable String userId) {
        return new Result(
                true,
                StatusCode.SUCCESS,
                "Find One Taxi User Success",
                toDtoConverter.convert(service.findById(userId))
        );
    }

    @Secured({ADMIN})
    @PostMapping
    public Result addUser(@Valid @RequestBody TaxiUser newUser) {
        return new Result(
                true,
                StatusCode.SUCCESS,
                "Add Taxi User Success",
                toDtoConverter.convert(service.save(newUser))
        );
    }

    @Secured({ADMIN, CLIENT})
    @PutMapping
    public Result update(@Valid @RequestBody TaxiUserDto updateDto) {
        TaxiUser update = toUserConverter.convert(updateDto);
        TaxiUser updated = service.update(update);
        TaxiUserDto updatedDto = toDtoConverter.convert(updated);
        return new Result(
                true,
                StatusCode.SUCCESS,
                "Update Taxi User Success",
                updatedDto
        );
    }

    @Secured({ADMIN})
    @PatchMapping("/{userId}/role")
    public Result updateRole(@PathVariable String userId, @RequestParam String role) {
        TaxiUser updated = service.updateRole(userId, role);
        TaxiUserDto updatedDto = toDtoConverter.convert(updated);
        return new Result(
                true,
                StatusCode.SUCCESS,
                "Update Taxi User Role Success",
                updatedDto
        );
    }

    @Secured({ADMIN})
    @DeleteMapping("/{userId}")
    public Result delete(@PathVariable String userId) {
        service.deleteById(userId);
        return new Result(
                true,
                StatusCode.SUCCESS,
                "Delete Taxi User Success"
        );
    }
}
