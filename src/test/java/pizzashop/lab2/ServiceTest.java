package pizzashop.lab2;

import org.junit.jupiter.api.*;
import pizzashop.model.PaymentType;
import pizzashop.repository.MenuRepository;
import pizzashop.repository.PaymentRepository;
import pizzashop.service.PizzaService;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import static org.junit.jupiter.api.Assertions.*;


public class ServiceTest {
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
    public void addPaymentECPTest() {
        int tableInt = 1;
        String tableString = "1.09";
        float amount = (float) 12.09;
        String amountString = "12.a";
        assertDoesNotThrow(() -> pizzaService.addPayment(tableInt, PaymentType.Cash, amount));
        assertThrows(NumberFormatException.class, () -> pizzaService.addPayment(Integer.parseInt(tableString), PaymentType.Cash, amount));
        assertThrows(NumberFormatException.class, () -> pizzaService.addPayment(tableInt, PaymentType.Cash, Float.parseFloat(amountString)));
        assertThrows(NumberFormatException.class, () -> pizzaService.addPayment(Integer.parseInt(tableString), PaymentType.Cash, Float.parseFloat(amountString)));
    }

    void basicTestBVA(int table, float amount) {
        assertDoesNotThrow(() -> pizzaService.addPayment(table, PaymentType.Cash, amount));
    }

    @Test
    public void addPaymentBVATest() {
        int table0 = 0;
        basicTestBVA(table0, (float) 12.09);

        int tableMin = Integer.MIN_VALUE;
        basicTestBVA(tableMin, (float) 12.09);

        int tableMinMinus1 = Integer.MIN_VALUE - 1;
        basicTestBVA(tableMinMinus1, (float) 12.09);

        int tableMinPlus1 = Integer.MIN_VALUE + 1;
        basicTestBVA(tableMinPlus1, (float) 12.09);

        int tableMax = Integer.MAX_VALUE;
        basicTestBVA(tableMax, (float) 12.09);
        int tableMaxMinus1 = Integer.MAX_VALUE - 1;
        basicTestBVA(tableMaxMinus1, (float) 12.09);
        int tableMaxPlus1 = Integer.MAX_VALUE + 1;
        basicTestBVA(tableMaxPlus1, (float) 12.09);

        int table5 = 5;
        basicTestBVA(table5, (float) 12.09);

        float amount0 = 0;
        basicTestBVA(0, amount0);

        float amountMin = Float.MIN_VALUE;
        basicTestBVA(0, amountMin);
        float amountMinMinus1 = Float.MIN_VALUE - 1;
        basicTestBVA(0, amountMinMinus1);

        float amountMinPlus1 = Float.MIN_VALUE + 1;
        basicTestBVA(0, amountMinPlus1);

        float amountMax = Float.MAX_VALUE;
        basicTestBVA(0, amountMax);
        float amountMaxMinus1 = Float.MAX_VALUE - 1;
        basicTestBVA(0, amountMaxMinus1);
        float amountMaxPlus1 = Float.MAX_VALUE + 1;
        basicTestBVA(0, amountMaxPlus1);
    }

    @Test
    public void soldTotalValid1Test() throws IOException {
        PaymentType paymentType = PaymentType.Cash;
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("data/payments.txt"));
        bufferedWriter.write("0,Cash,10.0\n");
        bufferedWriter.write("1,Card,10.0\n");
        bufferedWriter.write("3,Cash,12.0\n");
        bufferedWriter.write("2,Card,100.0\n");
        bufferedWriter.close();
        setUp();

        assertEquals(22.0, pizzaService.getTotalAmount(paymentType));
    }

    @Test
    public void soldTotalValid2Test() throws IOException {
        PaymentType paymentType = PaymentType.Cash;
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("data/payments.txt"));
        bufferedWriter.write("1,Card,10.0\n");
        bufferedWriter.write("2,Card,100.0\n");
        bufferedWriter.close();
        setUp();

        assertEquals(0.0, pizzaService.getTotalAmount(paymentType));
    }

    @Test
    public void soldTotalInvalidTest() throws IOException {
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("data/payments.txt"));
        bufferedWriter.write("1,Card,10.0\n");
        bufferedWriter.write("2,Card,100.0\n");
        bufferedWriter.close();
        setUp();

        assertThrows(IllegalArgumentException.class, () -> pizzaService.getTotalAmount(PaymentType.valueOf("cash")));
    }
}
