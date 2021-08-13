package stores.daily_balances;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import stores.daily_balances.entity.Product;
import stores.daily_balances.repository.ProductRepository;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;
import java.util.Scanner;

@SpringBootApplication
public class DailyBalancesForStoresApplication {

    @Autowired
    private ProductRepository productRepository;

    public static void main(String[] args) {
        SpringApplication.run(DailyBalancesForStoresApplication.class, args);
    }

    @PostConstruct
    public void initBalances() throws FileNotFoundException, URISyntaxException {
        Scanner scan = new Scanner(new File(Objects.requireNonNull(getClass().getResource("/static/csv/TotalBalancesOfAugust.csv")).toURI()));
        ArrayList<String[]> records = new ArrayList<>();
        String[] record;
        while(scan.hasNext()) {
            record = scan.nextLine().split(",");
            records.add(record);
        }

        Collection<Product> productCollection = new ArrayList<>();
        for(String[] temp : records) {
            Product balance = new Product();
            int counter = 0;
            for(String temp1 : temp) {
                if (counter == 0) balance.setId(Integer.parseInt(temp1));
                else if (counter == 1) balance.setName(temp1);
                else if (counter == 2) balance.setBadr(temp1);
                else if (counter == 3) balance.setBasos(temp1);
                else if (counter == 4) balance.setAlex(temp1);
                else if (counter == 5) balance.setTotal(temp1);
                else if (counter == 6) balance.setMerchantPrice(temp1);
                else if (counter == 7) balance.setFactoryPrice(temp1);
                else if (counter == 8) balance.setType(temp1);
                else balance.setCode(temp1);

                counter++;
            }
            productCollection.add(balance);
        }
        productRepository.saveAll(productCollection);
    }
}