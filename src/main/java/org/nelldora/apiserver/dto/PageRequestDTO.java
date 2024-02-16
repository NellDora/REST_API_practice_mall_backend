package org.nelldora.apiserver.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@SuperBuilder//상속을 해야할 경우를 대비 -> 상속을 받았을 때 빌더를 사용 못하는 것에 대한  개선
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageRequestDTO {

    @Builder.Default
    private int page =1;

    @Builder.Default
    private int size =10;
}
