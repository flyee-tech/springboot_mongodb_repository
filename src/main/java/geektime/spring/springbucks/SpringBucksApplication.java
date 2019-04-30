package geektime.spring.springbucks;

import geektime.spring.springbucks.converter.MoneyReadConverter;
import geektime.spring.springbucks.model.Coffee;
import geektime.spring.springbucks.model.CoffeeOrder;
import geektime.spring.springbucks.model.OrderState;
import geektime.spring.springbucks.repository.CoffeeRepository;
import geektime.spring.springbucks.service.CoffeeOrderService;
import geektime.spring.springbucks.service.CoffeeService;
import lombok.extern.slf4j.Slf4j;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.Optional;

@Slf4j
@EnableTransactionManagement
@SpringBootApplication
@EnableMongoRepositories
public class SpringBucksApplication implements ApplicationRunner {
    @Autowired
    private CoffeeRepository coffeeRepository;
    @Autowired
    private CoffeeService coffeeService;
    @Autowired
    private CoffeeOrderService orderService;

    public static void main(String[] args) {
        SpringApplication.run(SpringBucksApplication.class, args);
    }

    @Bean
    public MongoCustomConversions mongoCustomConversions() {
        return new MongoCustomConversions(Collections.singletonList(new MoneyReadConverter()));
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        coffeeRepository.deleteAll();
        initData();

        log.info("All Coffee: {}", coffeeRepository.findAll());

        Optional<Coffee> latte = coffeeService.findOneCoffee("Latte");

        log.info("latte.isPresent() -> {}",latte.isPresent());

        if (latte.isPresent()) {
            CoffeeOrder order = orderService.createOrder("Li Lei", latte.get());
            log.info("Update INIT to PAID: {}", orderService.updateState(order, OrderState.PAID));
            log.info("Update PAID to INIT: {}", orderService.updateState(order, OrderState.INIT));
        }

    }

    private void initData() {
        Coffee espresso = Coffee.builder().name("espresso").price(Money.ofMinor(CurrencyUnit.of("CNY"), 2000))
                .createTime(new Date()).updateTime(new Date()).build();
        Coffee latte = Coffee.builder().name("latte").price(Money.ofMinor(CurrencyUnit.of("CNY"), 2500))
                .createTime(new Date()).updateTime(new Date()).build();
        Coffee capuccino = Coffee.builder().name("capuccino").price(Money.ofMinor(CurrencyUnit.of("CNY"), 2500))
                .createTime(new Date()).updateTime(new Date()).build();
        Coffee mocha = Coffee.builder().name("mocha").price(Money.ofMinor(CurrencyUnit.of("CNY"), 3000))
                .createTime(new Date()).updateTime(new Date()).build();
        Coffee macchiato = Coffee.builder().name("macchiato").price(Money.ofMinor(CurrencyUnit.of("CNY"), 3000))
                .createTime(new Date()).updateTime(new Date()).build();

        coffeeRepository.saveAll(Arrays.asList(espresso, latte, capuccino, mocha, macchiato));
    }

}

