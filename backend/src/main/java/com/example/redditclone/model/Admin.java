package com.example.redditclone.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name="admin")
public class Admin{
    private @Id @GeneratedValue (strategy = GenerationType.IDENTITY) Long id;
    private Long userId;
}
