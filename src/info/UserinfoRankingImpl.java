package info;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import conn.ConnectionProvider;
import exceptions.DataIOException;

public class UserinfoRankingImpl implements UserinfoRanking {

	@Override
	public String getBestNic(int grade) throws DataIOException {
		
		String sql = "select project_game.user.no, nickname, score from project_game.user "
				+ "left outer join project_game.userscore on user.no = userscore.no "
				+ "order by score desc limit ?, 1";
		
		try(Connection conn = ConnectionProvider.makeConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {
				stmt.setInt(1, grade - 1);
				 
				try (ResultSet rs = stmt.executeQuery()) {
					if (rs.next()) {
						String bestNic  = rs.getString("nickname");
						return bestNic;
					}
				}
			
		} catch (SQLException e) {
			throw new DataIOException(e);
		}
		return null;
	}


	@Override
	public int getBestScore(int grade) throws DataIOException {
		
		String sql = "select project_game.user.no, nickname, score from project_game.user "
				+ "left outer join project_game.userscore on user.no = userscore.no "
				+ "order by score desc limit ?, 1";
		
		try(Connection conn = ConnectionProvider.makeConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {
				stmt.setInt(1, grade - 1);
				
				try (ResultSet rs = stmt.executeQuery()) {
					if (rs.next()) {
						int bestScore  = rs.getInt("score");
						return bestScore;
					}
				}
			
		} catch (SQLException e) {
			throw new DataIOException(e);
		}
		return 0;
	}


	@Override
	public int getBestcharacter(int grade) throws DataIOException {
		
		String sql = "SELECT roundCharacter FROM project_game.userscore "
				+ "order by score desc limit ?, 1;";
		
		try(Connection conn = ConnectionProvider.makeConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {
				stmt.setInt(1, grade - 1);
				
				try (ResultSet rs = stmt.executeQuery()) {
					if (rs.next()) {
						int bestCharacter  = rs.getInt(1);
						return bestCharacter;
					}
				}
			
		} catch (SQLException e) {
			throw new DataIOException(e);
		}
		return -1;
	}

}
