package com.module.price.service;

import com.module.price.client.CurrencyExchangeClient;
import com.module.price.dto.request.InvoiceDto;
import com.module.price.dto.request.ItemDto;
import com.module.price.dto.request.UserDto;
import com.module.price.enums.Currency;
import com.module.price.enums.ItemCategory;
import com.module.price.enums.UserType;
import com.module.price.rule.DiscountRule;
import com.module.price.service.impl.DiscountCalculationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class DiscountCalculationServiceImplTest {

    @InjectMocks
    private DiscountCalculationServiceImpl discountCalculationService;

    @Mock
    private CurrencyExchangeClient currencyExchangeClient;

    @Mock
    private DiscountRule affiliateDiscountRule;

    @Mock
    private DiscountRule employeeDiscountRule;

    @Mock
    private DiscountRule longTermCustomerDiscountRule;
    InvoiceDto invoiceDto = new InvoiceDto();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Mock discount rule behaviors
        when(affiliateDiscountRule.applyDiscount(anyDouble())).thenReturn(10.0);
        when(employeeDiscountRule.applyDiscount(anyDouble())).thenReturn(15.0);
        when(longTermCustomerDiscountRule.applyDiscount(anyDouble())).thenReturn(20.0);
        when(currencyExchangeClient.getExchangeRate("INR", "AED")).thenReturn(1.2);


        invoiceDto.setTotalAmount(300.0);
        invoiceDto.setItems(createItemDtoList());
        invoiceDto.setOriginalCurrency(Currency.ORIGINAL);
        invoiceDto.setTargetCurrency(Currency.TARGET);
    }

    @Test
    void testCalculatePayableAmountForEmployee() {
        // Arrange
        invoiceDto.setTotalAmount(200.0);
        invoiceDto.setUserDto(createEmployeeUser());
        invoiceDto.setItems(createItemDtoList());
        double result = discountCalculationService.calculatePayableAmount(invoiceDto);

        // Assert
        assertEquals(156, result); // Adjust based on actual expected value
    }

    @Test
    void testCalculatePayableAmountForAffiliate() {
        // Arrange

        invoiceDto.setUserDto(createAffiliateUser());
        invoiceDto.setItems(createItemDtoList());
        double result = discountCalculationService.calculatePayableAmount(invoiceDto);
        assertEquals(270.0, result); // Adjust based on actual expected value
    }

    @Test
    void testCalculatePayableAmountForLongTermCustomer() {

        invoiceDto.setItems(createItemDtoList());
        invoiceDto.setUserDto(createLongTermUser());
        double result = discountCalculationService.calculatePayableAmount(invoiceDto);

        // Assert
        assertEquals(342.0, result); // Adjust based on actual expected value
    }

    public static List<ItemDto> createItemDtoList() {
        List<ItemDto> itemList = new ArrayList<>();

        itemList.add(ItemDto.builder()
                .id(1L)
                .itemName("Item 1")
                .category(ItemCategory.GROCERY) // Use your actual categories
                .totalAmount(100.0)
                .build());

        itemList.add(ItemDto.builder()
                .id(2L)
                .itemName("Item 2")
                .category(ItemCategory.NORMAL) // Use your actual categories
                .totalAmount(200.0)
                .build());

        itemList.add(ItemDto.builder()
                .id(3L)
                .itemName("Item 3")
                .category(ItemCategory.GROCERY) // Use your actual categories
                .totalAmount(300.0)
                .build());

        return itemList;
    }

    public static UserDto createLongTermUser() {
        return UserDto.builder().userId("1").customerTenure(1).userType(UserType.LONG_TERM_CUSTOMER).build();
    }
    public static UserDto createEmployeeUser() {
        return UserDto.builder().userId("1").customerTenure(1).userType(UserType.EMPLOYEE).build();
    }
    public static UserDto createAffiliateUser() {
        return UserDto.builder().userId("1").customerTenure(1).userType(UserType.EMPLOYEE).build();
    }
}
