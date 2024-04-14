package com.cby.tcs.handler;

import cn.dev33.satoken.exception.NotLoginException;
import com.freedom.cloud.exception.BasicFormatException;
import com.freedom.cloud.result.ResultEntity;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.sql.SQLException;

@ControllerAdvice
public class ErrorExceptionHandler {

  @ExceptionHandler(value = BasicFormatException.class)
  public ResultEntity ResultException(BasicFormatException exception){
    return new ResultEntity(exception.getMessage(), HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(SQLException.class)
  public ResultEntity SQLException(SQLException exception){
    return new ResultEntity("数据库产生了异常", HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler(NotLoginException.class)
  public ResultEntity SQLException(NotLoginException exception){
    return new ResultEntity(exception.getMessage(), HttpStatus.UNAUTHORIZED);
  }

  @ExceptionHandler(BindException.class)
  public ResultEntity SQLException(BindException exception){
    String errorText = "";
    for (FieldError error : exception.getBindingResult().getFieldErrors()) {
      errorText = error.getDefaultMessage();
      break;
    }
    return new ResultEntity(errorText, HttpStatus.BAD_REQUEST);
  }

}
