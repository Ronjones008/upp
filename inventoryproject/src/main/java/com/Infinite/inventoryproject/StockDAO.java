package com.Infinite.inventoryproject;


import java.io.IOException;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.transaction.Transaction;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

@ManagedBean
@SessionScoped
public class StockDAO {
	
	SessionFactory sessionFactory;
	//generate Stock id
	
	public String GenerateStockid() {	
		sessionFactory = SessionHelper.getConnection();
		Session session = sessionFactory.openSession();
		Criteria cr = session.createCriteria(StockDetails.class);
		List<StockDetails> stockList = cr.list();
		session.close();
		if( stockList.size()==0) {
			return "S001";
	
		}
		else {
			String id = stockList.get(stockList.size()-1).getStockid();
			int id1 = Integer.parseInt(id.substring(1));
			id1++;
			String id2 = String.format("S%03d", id1);
     		return id2;		
		}	
		
	}
	
	
	//Add StockDetails
	
//	
//	public String addstockDetails(StockDetails stockDetails) throws IOException {
//		sessionFactory = SessionHelper.getConnection();
//		Session session = sessionFactory.openSession();
//		String stockid=GenerateStockid();
//		stockDetails.setStockid(stockid);
//		Criteria cr = session.createCriteria(StockDetails.class);
//		cr.add(Restrictions.eq("itemName", stockDetails.getItemName()));
//		List<StockDetails> stockList = cr.list();
//		FacesContext context = FacesContext.getCurrentInstance();
//        ExternalContext externalContext = context.getExternalContext();
//        
//        if (stockList.size() >= 1) {
//        	externalContext.redirect("Addstock.xhtml?exist=Stock already exist");
// 	        return null;
//        } else {	
//	        org.hibernate.Transaction tran =  session.beginTransaction();
//			session.save(stockDetails);
//			tran.commit();
//	      return "ShowStock.jsp?faces-redirect=true";
//        }
//    }

	public String addstockDetails(StockDetails stockDetails) {
	sessionFactory = SessionHelper.getConnection();
		Session session = sessionFactory.openSession();
		String stockid=GenerateStockid();
		stockDetails.setStockid(stockid);
		Criteria cr = session.createCriteria(StockDetails.class);
		boolean  b = searchitem(stockDetails.getItemName());
		if(b == true) {
			return "Is All Ready Exits ";
		}
		else {
		org.hibernate.Transaction tran =  session.beginTransaction();
		session.save(stockDetails);
		tran.commit();
		return "Stock details Added";
		}
	}
	
		
	
	
		public List<StockDetails> ShowStock() {
			sessionFactory = SessionHelper.getConnection();
			Session session = sessionFactory.openSession();
			Criteria cr = session.createCriteria(StockDetails.class);
			List<StockDetails> stockList = cr.list();
			return stockList;
			
		}
		
		public StockDetails searchStock(String stockid) {
			sessionFactory = SessionHelper.getConnection();
			Session session = sessionFactory.openSession(); 
	
			return (StockDetails) session.createQuery("from StockDetails where stockid = :stockid").setParameter("stockid", stockid).list().get(0);
		}
		
		
		public boolean searchitem(String stockname){
			sessionFactory = SessionHelper.getConnection();
			Session session = sessionFactory.openSession();
			
			Criteria cr = session.createCriteria(StockDetails.class);
			cr.add(Restrictions.eq("itemName",stockname));
			List<Customer> itemList=cr.list();
		    if(itemList.size() > 0) {
		    	return true;
		    }
		    else {
		    	return false;
		    }
			
		}
		
		public String updateStock(StockDetails stockDetails){
			sessionFactory = SessionHelper.getConnection();
			Session session = sessionFactory.openSession();
			org.hibernate.Transaction transaction = session.beginTransaction();
			session.saveOrUpdate(stockDetails);
			transaction.commit();
			return "Stock updated Successsfully...";
		}
		
		
		
		
//		public void validateitemName(FacesContext context, UIComponent comp, Object value)
//		{ 
//			System.out.println("inside validate method"); 
//		String mno = (String) value;
//		boolean flag=false;
//		if (mno.matches("\\d{10}")) 
//		{ 
//			flag=true;
//		}
//		if(flag==false)
//		{
//			((UIInput) comp).setValid(false);
//		FacesMessage message = new FacesMessage( "invalid itemName");
//		context.addMessage(comp.getClientId(context), message);
//		} 
//		}	
//		public void validatezipcode(FacesContext context, UIComponent comp,
//				Object value) {
//
//			System.out.println("inside validate method");
//
//			String zno = (String) value;
//			boolean flag=false;
//			if (zno.matches("\\d{6}"))  
//			{ flag=true;}
//
//			if(flag==false) {
//				((UIInput) comp).setValid(false);
//
//				FacesMessage message = new FacesMessage(
//						"invalid zipcode");
//				context.addMessage(comp.getClientId(context), message); 
//			}
//
//}
}	
	


