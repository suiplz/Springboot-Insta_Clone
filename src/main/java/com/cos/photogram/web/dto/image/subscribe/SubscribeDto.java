package com.cos.photogram.web.dto.image.subscribe;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class SubscribeDto {
    private int userId;
    private String username;
    private String profileImageUrl;
    private Integer subscribeState;
    private Integer equalUserState;

}
