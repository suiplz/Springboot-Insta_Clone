package com.cos.photogram.web.dto;

import com.cos.photogram.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CMRespDto<T> {
    private int code; // 1(성공), -1(실패)
    private String message;
    private T data;

}
