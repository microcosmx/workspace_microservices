package price.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * Created by Chenjie Xu on 2017/6/9.
 */
@Document(collection="price")
public class Price {


    @NotNull
    @Valid
    private double price;
}
