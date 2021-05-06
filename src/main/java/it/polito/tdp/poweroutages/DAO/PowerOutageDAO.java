package it.polito.tdp.poweroutages.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import it.polito.tdp.poweroutages.model.Nerc;
import it.polito.tdp.poweroutages.model.PowerOutage;

public class PowerOutageDAO {
	
	public List<Nerc> getNercList() {

		String sql = "SELECT id, value FROM nerc";
		List<Nerc> nercList = new ArrayList<>();

		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();

			while (res.next()) {
				Nerc n = new Nerc(res.getInt("id"), res.getString("value"));
				nercList.add(n);
			}

			conn.close();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

		return nercList;
	}

	
	public List<PowerOutage> getOutagesByNerc(Nerc nerc){
		
			String sql = "SELECT id, customers_affected, date_event_began, date_event_finished "
					+ "FROM poweroutages "
					+ "WHERE nerc_id = ?";
			
			List<PowerOutage> nercList = new ArrayList<>();

			try {
				Connection conn = ConnectDB.getConnection();
				PreparedStatement st = conn.prepareStatement(sql);
				st.setInt(1, nerc.getId());
				ResultSet res = st.executeQuery();

				while (res.next()) {
					int poId = res.getInt("id");
					int customers = res.getInt("customers_affected");
					LocalDateTime dateBegin = res.getTimestamp("date_event_began").toLocalDateTime();
					LocalDateTime dateFinish = res.getTimestamp("date_event_finished").toLocalDateTime();
					
					PowerOutage po = new PowerOutage(poId, nerc, customers, dateBegin, dateFinish);
					nercList.add(po);
				}

				conn.close();

			} catch (SQLException e) {
				throw new RuntimeException(e);
			}
			
			return nercList;
		
	}
	
	

}
