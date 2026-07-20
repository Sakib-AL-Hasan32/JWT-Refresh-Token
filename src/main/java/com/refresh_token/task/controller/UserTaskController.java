package com.refresh_token.task.controller;

import com.refresh_token.common.constants.ApiEndpoints;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(ApiEndpoints.Auth.BASE)
public class UserTaskController {
}
