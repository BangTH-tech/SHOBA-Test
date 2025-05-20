package com.project_shoba_test.SHOBA_TEST.service.user.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.project_shoba_test.SHOBA_TEST.exception.BadRequestException;
import com.project_shoba_test.SHOBA_TEST.exception.NotFoundException;
import com.project_shoba_test.SHOBA_TEST.model.dto.request.user.AddUserDto;
import com.project_shoba_test.SHOBA_TEST.model.dto.request.user.EditUserDto;
import com.project_shoba_test.SHOBA_TEST.model.dto.request.user.FilterEmployeeListDto;
import com.project_shoba_test.SHOBA_TEST.model.dto.response.user.EmployeeDetailResponse;
import com.project_shoba_test.SHOBA_TEST.model.dto.response.user.EmployeeListResponse;
import com.project_shoba_test.SHOBA_TEST.model.entity.Users;
import com.project_shoba_test.SHOBA_TEST.model.enums.UserStatus;
import com.project_shoba_test.SHOBA_TEST.repository.UserRepository;
import com.project_shoba_test.SHOBA_TEST.service.user.UserService;
import com.project_shoba_test.SHOBA_TEST.utils.PasswordUtil;
import com.project_shoba_test.SHOBA_TEST.utils.mapper.user.AddUserMapper;
import com.project_shoba_test.SHOBA_TEST.utils.mapper.user.EditUserMapper;
import com.project_shoba_test.SHOBA_TEST.utils.mapper.user.EmployeeDetailMapper;
import com.project_shoba_test.SHOBA_TEST.utils.mapper.user.EmployeeListMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final EmployeeListMapper employeeListMapper;

    private final AddUserMapper addUserMapper;

    private final EditUserMapper editUserMapper;

    private final EmployeeDetailMapper employeeDetailMapper;

    @Override
    public Page<EmployeeListResponse> getAllEmployees(FilterEmployeeListDto filterEmployeeListDto) {

        Pageable pageable;
        if (filterEmployeeListDto.getSortBy() != null &&
                (filterEmployeeListDto.getSortBy().equals("id") ||
                        filterEmployeeListDto.getSortBy().equals("fullName") ||
                        filterEmployeeListDto.getSortBy().equals("username") ||
                        filterEmployeeListDto.getSortBy().equals("email"))) {
            Sort sort = filterEmployeeListDto.isAscending() ? Sort.by(filterEmployeeListDto.getSortBy()).ascending()
                    : Sort.by(filterEmployeeListDto.getSortBy()).descending();
            pageable = PageRequest.of(filterEmployeeListDto.getPage(), filterEmployeeListDto.getSize(), sort);
        } else {
            pageable = PageRequest.of(filterEmployeeListDto.getPage(), filterEmployeeListDto.getSize());
        }
        Page<Users> employeeList = userRepository.getEmployeeList(
                filterEmployeeListDto.getSearch(),
                filterEmployeeListDto.getRole(),
                filterEmployeeListDto.getStatus(),
                pageable);
        return employeeList.map(employeeListMapper::mapTo);
    }

    @Override
    public void addUser(AddUserDto addUserDto) {
        if (userRepository.existsByUsernameOrEmail(addUserDto.getUsername()).isPresent()) {
            throw new BadRequestException("Username already exists", "Username already exists");
        }
        if (userRepository.existsByUsernameOrEmail(addUserDto.getEmail()).isPresent()) {
            throw new BadRequestException("Email already exists", "Email already exists");
        }

        Users user = addUserMapper.mapFrom(addUserDto);
        String randomPassword = PasswordUtil.generateRandomPassword();
        user.setPassword(PasswordUtil.encodePassword(randomPassword));
        user.setStatus(UserStatus.ACTIVE);
        userRepository.save(user);
    }

    @Override
    public void editUser(EditUserDto editUserDto) {
        Users user = userRepository.findById(editUserDto.getId())
                .orElseThrow(() -> new NotFoundException("User not found", "User not found"));
        if (!user.getUsername().equals(editUserDto.getUsername())
                && userRepository.existsByUsernameOrEmail(editUserDto.getUsername()).isPresent()) {
            throw new BadRequestException("Username already exists", "Username already exists");
        }
        if (!user.getEmail().equals(editUserDto.getEmail())
                && userRepository.existsByUsernameOrEmail(editUserDto.getEmail()).isPresent()) {
            throw new BadRequestException("Email already exists", "Email already exists");
        }

        user = editUserMapper.mapFrom(editUserDto);
        log.info(user);
        String randomPassword = PasswordUtil.generateRandomPassword();
        user.setPassword(PasswordUtil.encodePassword(randomPassword));

        userRepository.save(user);
    }

    @Override
    public void changeUserStatus(Long id) {
        Users user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found", "User not found"));
        user.setStatus(user.getStatus() == UserStatus.ACTIVE ? UserStatus.INACTIVE : UserStatus.ACTIVE);
        userRepository.save(user);
    }

    @Override
    public EmployeeDetailResponse getEmployeeDetailResponse(Long id) {
        Users user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found", "User not found"));
        return employeeDetailMapper.mapTo(user);
    }

}
