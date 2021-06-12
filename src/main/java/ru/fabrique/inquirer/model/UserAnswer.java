package ru.fabrique.inquirer.model;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user_answers")
public class UserAnswer extends BaseEntity{
    @ManyToOne
    private User user;
    @ManyToOne
    private Poll poll;
    @ManyToOne
    private Question question;
    @ManyToMany(cascade = CascadeType.ALL)
    private List<Answer> answers = new ArrayList<>();
}
