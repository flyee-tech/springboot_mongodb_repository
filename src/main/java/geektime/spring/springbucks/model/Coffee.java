package geektime.spring.springbucks.model;

import lombok.*;
import org.joda.money.Money;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Date;

@Document
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class Coffee extends BaseEntity implements Serializable {
    private String name;
    private Money price;

    @Builder
    public Coffee(String id, Date createTime, Date updateTime, String name, Money price) {
        super(id, createTime, updateTime);
        this.name = name;
        this.price = price;
    }

}
