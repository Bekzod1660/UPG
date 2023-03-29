package com.example.upg.entity;

import com.example.upg.dto.AdminRequestDto;
import com.example.upg.dto.UserDto;
import com.example.upg.entity.role.PermissionEnum;
import com.example.upg.entity.role.RoleEnum;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class UserEntity extends Base implements UserDetails {

    private String name;
    @Column(unique = true)
    private String username;
    private String password;
    @Column(unique = true)
    private String email;
    private boolean isActive;
    @Column(unique = true)
    private int phoneNumber;
    @Enumerated(value = EnumType.STRING)
    private List<RoleEnum> perRoleEnumList;
    @Enumerated(value = EnumType.STRING)
    private List<PermissionEnum> permissionEnumList;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority>roles=new ArrayList<>();
        perRoleEnumList.forEach((role)->{
            roles.add(new SimpleGrantedAuthority("ROLE_"+role));

        });
        permissionEnumList.forEach((per)->{
            roles.add(new SimpleGrantedAuthority(per.name()));
        });
        return roles;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public static UserEntity ofUser(UserDto userRequestDto) {

        return UserEntity.builder()
                .email(userRequestDto.getEmail())
                .name(userRequestDto.getName())
                .phoneNumber(userRequestDto.getPhoneNumber())
                .username(userRequestDto.getUsername())
                .permissionEnumList(List.of())
                .perRoleEnumList(Collections.singletonList(RoleEnum.USER))
                .isActive(true)
                .build();
    }

    public static UserEntity ofAdmin(AdminRequestDto adminRequestDto) {
        return UserEntity.builder()
                .name(adminRequestDto.getName())
                .email(adminRequestDto.getEmail())
                .phoneNumber(adminRequestDto.getPhoneNumber())
                .username(adminRequestDto.getUsername())
                .build();


    }
}
