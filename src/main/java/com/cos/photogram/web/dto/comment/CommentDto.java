package com.cos.photogram.web.dto.comment;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class CommentDto {
    @NotBlank
    private String content;

    @NotNull
    private Integer imageId;


}
