package com.example.wdpai2backend.entity;

import com.example.wdpai2backend.entity.AppUser;
import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name="authority")
public class Authority implements GrantedAuthority {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id_auth;
   String authority;


   public Authority() {
   }

   public Authority(String authority) {
      this.authority = authority;
   }

   @Override
   public String getAuthority() {
      return authority;
   }

   public void setId_auth(Long id) {
      this.id_auth = id;
   }

   @Id
   public Long getId_auth() {
      return id_auth;
   }

   public void setAuthority(String authority) {
      this.authority = authority;
   }


}
