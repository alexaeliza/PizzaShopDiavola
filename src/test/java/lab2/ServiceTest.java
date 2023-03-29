package lab2;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import pizzashop.model.PaymentType;
import pizzashop.repository.MenuRepository;
import pizzashop.repository.PaymentRepository;
import pizzashop.service.PizzaService;

import java.io.Serializable;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ServiceTest implements Serializable, Cloneable {
    static PizzaService pizzaService;
    static MenuRepository menuRepository;
    static PaymentRepository paymentRepository;

    @BeforeAll
    static void setUp() {
        menuRepository = new MenuRepository();
        paymentRepository = new PaymentRepository();
        pizzaService = new PizzaService(menuRepository, paymentRepository);
    }

    @Test
    void addPaymentTestECP() {
        int tableInt = 1;
        String tableString = "1.09";
        float amount = (float) 12.09;
        String amountString = "12.a";
        assertDoesNotThrow(() -> pizzaService.addPayment(tableInt, PaymentType.Cash, amount));
        assertThrows(NumberFormatException.class, () -> pizzaService.addPayment(Integer.parseInt(tableString), PaymentType.Cash, amount));
        assertThrows(NumberFormatException.class, () -> pizzaService.addPayment(tableInt, PaymentType.Cash, Float.parseFloat(amountString)));
        assertThrows(NumberFormatException.class, () -> pizzaService.addPayment(Integer.parseInt(tableString), PaymentType.Cash, Float.parseFloat(amountString)));
    }

    @Test
    void addPaymentTestBVA() {
        int table0 = 0;
        assertDoesNotThrow(() -> pizzaService.addPayment(table0, PaymentType.Cash, 12.09));

        int tableMin = Integer.MIN_VALUE;
        assertDoesNotThrow(() -> pizzaService.addPayment(tableMin, PaymentType.Cash, 12.09));
        int tableMinMinus1 = Integer.MIN_VALUE - 1;
        assertThrows(ArithmeticException.class, () -> pizzaService.addPayment(tableMinMinus1, PaymentType.Cash, 12.09));
//        assertDoesNotThrow(()-> pizzaService.addPayment(tableMinMinus1, PaymentType.Cash,12.09));
        int tableMinPlus1 = Integer.MIN_VALUE + 1;
        assertDoesNotThrow(() -> pizzaService.addPayment(tableMinPlus1, PaymentType.Cash, 12.09));

        int tableMax = Integer.MAX_VALUE;
        assertDoesNotThrow(() -> pizzaService.addPayment(tableMax, PaymentType.Cash, 12.09));
        int tableMaxMinus1 = Integer.MAX_VALUE - 1;
        assertDoesNotThrow(() -> pizzaService.addPayment(tableMaxMinus1, PaymentType.Cash, 12.09));
        int tableMaxPlus1 = Integer.MAX_VALUE + 1;
        assertThrows(ArithmeticException.class, () -> pizzaService.addPayment(tableMaxPlus1, PaymentType.Cash, 12.09));
//        assertDoesNotThrow(()-> pizzaService.addPayment(tableMaxPlus1, PaymentType.Cash,12.09));

        int table5 = 5;
        assertDoesNotThrow(() -> pizzaService.addPayment(table5, PaymentType.Cash, 12.09));

        float amount0 = 0;
        assertDoesNotThrow(() -> pizzaService.addPayment(0, PaymentType.Cash, amount0));

        float amountMin = Float.MIN_VALUE;
        assertDoesNotThrow(() -> pizzaService.addPayment(0, PaymentType.Cash, amountMin));
        float amountMinMinus1 = Float.MIN_VALUE - 1;
        assertThrows(ArithmeticException.class, () -> pizzaService.addPayment(0, PaymentType.Cash, amountMinMinus1));
//       assertDoesNotThrow(()-> pizzaService.addPayment(0, PaymentType.Cash,amountMinMinus1));
        float amountMinPlus1 = Float.MIN_VALUE + 1;
        assertDoesNotThrow(() -> pizzaService.addPayment(0, PaymentType.Cash, amountMinPlus1));

        float amountMax = Float.MAX_VALUE;
        assertDoesNotThrow(() -> pizzaService.addPayment(0, PaymentType.Cash, amountMax));
        float amountMaxMinus1 = Float.MAX_VALUE - 1;
        assertDoesNotThrow(() -> pizzaService.addPayment(0, PaymentType.Cash, amountMaxMinus1));
        float amountMaxPlus1 = Float.MAX_VALUE + 1;
        assertThrows(ArithmeticException.class, () -> pizzaService.addPayment(0, PaymentType.Cash, amountMaxPlus1));
//        assertDoesNotThrow(()-> pizzaService.addPayment(0, PaymentType.Cash,amountMaxPlus1));
    }
}
