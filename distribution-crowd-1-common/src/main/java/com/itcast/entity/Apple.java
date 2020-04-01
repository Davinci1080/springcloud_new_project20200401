package com.itcast.entity;

import lombok.*;

/*@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode*/
@Data//配合一个AllArgsConstruction和npArgsConstruction一起用
@AllArgsConstructor
@NoArgsConstructor

//注意alter+7可以查看类的所有方法
public class Apple {
    private Integer appleId;
    private String appleName;
    private Double applePrice;
}
