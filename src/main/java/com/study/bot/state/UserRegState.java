package com.study.bot.state;

import com.study.bot.state.stage.UserRegStage;
import com.study.bot.entity.type.UserRoles;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserRegState {

    private String name;
    private String surname;
    private UserRoles role;
    private UserRegStage stage = UserRegStage.START;
}
