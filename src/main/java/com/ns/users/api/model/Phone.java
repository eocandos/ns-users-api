package com.ns.users.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.io.Serializable;


@Getter
@Setter
@Entity
@Table(name="phone")
public class Phone implements Serializable {
    @Id
    @GeneratedValue
    private Long id;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="userId")
    private Phone phone;

    private Integer number;

    private Integer cityCode;

    private Integer contryCode;

}
