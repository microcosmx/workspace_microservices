package inside_payment.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.UUID;

/**
 * Created by Administrator on 2017/6/20.
 */
@Document(collection="payment")
public class Payment {
    @Id
    @NotNull
    @Valid
    private UUID id;

    @NotNull
    @Valid
    private String orderNumber;

    @NotNull
    @Valid
    private String price;

    public Payment(){}

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
