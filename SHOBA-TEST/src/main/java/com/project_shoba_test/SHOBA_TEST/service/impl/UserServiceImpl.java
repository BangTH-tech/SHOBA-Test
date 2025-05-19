package com.project_shoba_test.SHOBA_TEST.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.project_shoba_test.SHOBA_TEST.model.dto.request.FilterEmployeeListDto;
import com.project_shoba_test.SHOBA_TEST.model.dto.response.EmployeeListResponse;
import com.project_shoba_test.SHOBA_TEST.model.entity.Users;
import com.project_shoba_test.SHOBA_TEST.repository.UserRepository;
import com.project_shoba_test.SHOBA_TEST.service.UserService;
import com.project_shoba_test.SHOBA_TEST.utils.mapper.EmployeeListMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final EmployeeListMapper employeeListMapper;

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
                pageable
        );
        return employeeList.map(employeeListMapper::mapTo);
    }

}
