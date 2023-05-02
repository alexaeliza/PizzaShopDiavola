package pizzashop.lab4;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import pizzashop.model.MenuDataModel;
import pizzashop.model.Payment;
import pizzashop.model.PaymentType;
import pizzashop.repository.MenuRepository;
import pizzashop.repository.PaymentRepository;
import pizzashop.service.PizzaService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PizzaServiceTest {
    static PizzaService pizzaService;
    static MenuRepository menuRepo;
    static PaymentRepository payRepo;

    @BeforeAll
    static void setUp(){
        menuRepo=mock(MenuRepository.class);
        payRepo=mock(PaymentRepository.class);
        pizzaService=new PizzaService(menuRepo,payRepo);
    }

    @Test
    void getTotalAmountTest(){
        Payment payment1=new Payment(1, PaymentType.Cash,10.0);
        Payment payment2=new Payment(1, PaymentType.Card,15.0);
        when(payRepo.getAll()).thenReturn(List.of(payment1,payment2));
        assertEquals(10.0, pizzaService.getTotalAmount(PaymentType.Cash));
    }

    @Test
    void addPaymentTest(){
        doNothing().when(payRepo).add(any(Payment.class));
        assertDoesNotThrow(() -> pizzaService.addPayment(1,PaymentType.Cash,10.0));
    }
}
