package order.service;

import order.dao.OrderDAOInterface;
import order.dao.OrderJDBCDAOImpl;


public class PayService {
    private OrderDAOInterface dao;

    public PayService() {
        dao = new OrderJDBCDAOImpl();
    }
    public int orderPay(Integer orderID) {
    	return dao.orderPay(orderID);
    }
}
