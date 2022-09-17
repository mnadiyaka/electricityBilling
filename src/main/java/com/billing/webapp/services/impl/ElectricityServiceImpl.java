package com.billing.webapp.services.impl;

import com.billing.webapp.model.dto.ElectricityDto;
import com.billing.webapp.model.entity.Electricity;
import com.billing.webapp.model.entity.ElectricityHolder;
import com.billing.webapp.model.entity.History;
import com.billing.webapp.repository.ElectricityRepository;
import com.billing.webapp.services.ElectricityService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
@Slf4j
public class ElectricityServiceImpl implements ElectricityService {

    private final ElectricityRepository electricityRepository;

    private final MongoTemplate mongoTemplate;

    @Override
    public Electricity getById(String id) {
        return electricityRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Electricity with " + id + "doesn't exist"));
    }

    @Override
    public Electricity createElectricity(ElectricityDto electricityDto) {
        Electricity electricity = ElectricityDto.toElectricity(electricityDto);
//        electricity.setDiscount(Optional.ofNullable(electricity.getDiscount()).isPresent()?electricity.getDiscount():Discount.NONE.getDiscount());
        return electricityRepository.insert(ElectricityDto.toElectricity(electricityDto));
    }

    @Override
    public Electricity updateElectricity(String id, ElectricityDto electricityDto) {
        Electricity electricity = getById(id);
        Optional.ofNullable(electricity.getTariff()).ifPresent(electricity::setTariff);
        Optional.ofNullable(electricity.getMonthPaid()).ifPresent(electricity::setMonthPaid);
        Optional.ofNullable(electricity.getTotalPaid()).ifPresent(electricity::setTotalPaid);
        Optional.ofNullable(electricity.getMonthAmountSpend()).ifPresent(electricity::setMonthAmountSpend);
        Optional.ofNullable(electricity.getTotalAmountSpend()).ifPresent(electricity::setTotalAmountSpend);
        Optional.ofNullable(electricity.getDiscount()).ifPresent(electricity::setDiscount);

        return electricityRepository.save(electricity);
    }

    @Override
    public void deleteElectricity(String id) {
        Electricity electricity = getById(id);
        electricityRepository.delete(electricity);
    }

    @Override
    public void insertMonthSpend(String id, Integer data) {
        Electricity electricity = getById(id);
        electricity.setTotalAmountSpend(electricity.getMonthAmountSpend());
        electricity.setMonthAmountSpend(data);
        electricity.setToPay(countBill(electricity));
        electricity.setMonthPaid(0.0);
        electricity.setTotalPaid(electricity.getTotalPaid() + electricity.getMonthPaid());
        electricity.setDate(LocalDate.now());

        electricityRepository.save(electricity);
    }

    @Override
    public void addDiscount(String id, Double discount) {
        Electricity electricity = getById(id);
        electricity.setDiscount(discount);

        electricityRepository.save(electricity);
    }

    private Double countBill(Electricity electricity) {
        return electricity.getTariff() * (electricity.getMonthAmountSpend() - electricity.getTotalAmountSpend()) * (1.0 - electricity.getDiscount());
    }

    @Override
    public List<ElectricityDto> unpaidThroughMonth(int month) {

        Query query = new Query(Criteria.where("toPay").gt(0.0));
        return mongoTemplate.find(query, ElectricityDto.class);
    }

    @Override
    public String pay(String id, Double price) {
        Electricity electricity = getById(id);
        electricity.setMonthPaid(electricity.getToPay());
        electricity.setToPay(electricity.getToPay() - price);
        electricity.setTotalPaid(electricity.getTotalPaid() + electricity.getMonthPaid());
        electricity.setDate(LocalDate.now());
        Set<History> histories = electricity.getHistory();
        histories.add(new History(LocalDate.now(), electricity.getMonthAmountSpend(), electricity.getMonthPaid()));
        return "paid";
    }

    @Override
    public Set<History> showHistory(String id) {
        Electricity electricity = getById(id);

        return electricity.getHistory();
    }

    @Override
    @SneakyThrows
    @Async
    public CompletableFuture<Electricity> asyncInsertNewMonthSpend(String id, Integer data, String key) {
        Electricity electricity = getById(id);
        electricity.setTotalAmountSpend(electricity.getMonthAmountSpend());
        electricity.setMonthAmountSpend(data);
        electricity.setToPay(countBill(electricity));
        electricity.setMonthPaid(0.0);
        electricity.setTotalPaid(electricity.getTotalPaid() + electricity.getMonthPaid());
        electricity.setDate(LocalDate.now());

        electricityRepository.save(electricity);


        Thread.sleep(15000L);
        log.info("async works");

        CompletableFuture<Electricity> future = CompletableFuture.completedFuture(electricity);
        ElectricityHolder.setData(key, new ElectricityHolder.Temp().setFuture(future).setState(ElectricityHolder.State.COMPLETED));

        return future;
    }
}
