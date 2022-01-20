package com.jojoldu.book.springboot.domain;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Getter
@MappedSuperclass //상속받을시 모든 entity 클래스들의 컬럼에 이 클래스의 필드들이 생성
@EntityListeners(AuditingEntityListener.class)
public class BaseTimeEntity {

    @CreatedDate//생성될 때 시간 자동 저장
    private LocalDateTime createdDate;

    @LastModifiedDate
    private LocalDateTime modifiedDate;

}
