package info;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import conn.ConnectionProvider;
import exceptions.DataIOException;

public class UserinfoRepositoryImpl implements UserinfoRepository {
//id중복체크
	@Override
	public int countByID(String inputId) throws DataIOException {
		String sql = "SELECT count(*) FROM project_game.user where id = ?";
		try (Connection conn = ConnectionProvider.makeConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setString(1, inputId);

			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					return rs.getInt(1);
				}
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new DataIOException(e);
		}
		return 0;
	}

//닉네임중복체크
	@Override
	public int countByNickName(String inputNickname) throws DataIOException {
		String sql = "SELECT count(*) FROM project_game.user where nickname = ?";
		try (Connection conn = ConnectionProvider.makeConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setString(1, inputNickname);

			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					return rs.getInt(1);
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new DataIOException(e);
		}
		return 0;
	}

//회원가입
	@Override // 회원가입
	public int insert(String inputId, String inputPassword, String inputNickname) throws DataIOException  {
		String sql = "INSERT INTO project_game.user (id, password, nickname) values (?, ?, ?)";

		try (Connection conn = ConnectionProvider.makeConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setString(1, inputId);
			stmt.setString(2, inputPassword);
			stmt.setString(3, inputNickname);

			return stmt.executeUpdate();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new DataIOException(e);
		}
	}

//마지막가입자 pk	
	@Override
	public int lastNo() throws DataIOException  {
		String sqlLastNo = "SELECT no FROM project_game.user ORDER BY no desc limit 1;";

		try (Connection conn = ConnectionProvider.makeConnection();
				PreparedStatement stmt2 = conn.prepareStatement(sqlLastNo)) {

			try (ResultSet rs2 = stmt2.executeQuery()) {
				if (rs2.next()) {
					return rs2.getInt(1);
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new DataIOException(e);
		}
		return 0;
	}

//로그인
	@Override
	public int login(String inputId, String inputPassword) throws DataIOException   {
		String sql = "SELECT count(*) FROM project_game.user where id = ? and password = ?";
		try (Connection conn = ConnectionProvider.makeConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setString(1, inputId);
			stmt.setString(2, inputPassword);

			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					return rs.getInt(1);
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new DataIOException(e);
		}
		return 0;
	}

	@Override
	public int setMoney(int myNo) throws DataIOException {
		String sql1 = "UPDATE project_game.user SET money = (SELECT sum(roundMoney) From project_game.userscore WHERE no = ?) where no = ?";
		
		try (Connection conn = ConnectionProvider.makeConnection();
				PreparedStatement stmt1 = conn.prepareStatement(sql1)) {
			stmt1.setInt(1, myNo);
			stmt1.setInt(2, myNo);

			return stmt1.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
			throw new DataIOException(e);
		}
	}
//회원의 보유머니
	@Override
	public int countMoney(String inputId) throws DataIOException  {
		String sql2 = "SELECT money FROM project_game.user where id = ?";
		
		try (Connection conn = ConnectionProvider.makeConnection();
				PreparedStatement stmt2 = conn.prepareStatement(sql2)) {
			stmt2.setString(1, inputId);
			try (ResultSet rs = stmt2.executeQuery()) {
				if (rs.next()) {
					return rs.getInt(1);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DataIOException(e);
		}
		return -1;
	}

//캐릭터 값만큼 돈빼기
	@Override
	public int changeMoney(String inputId, int price) throws DataIOException {
		String sql = "UPDATE project_game.user SET money = money - ? WHERE id = ?";
		try (Connection conn = ConnectionProvider.makeConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setInt(1, price);
			stmt.setString(2, inputId);
			return stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DataIOException(e);
		}
	}

//캐릭터 보유시 1
	@Override
	public int buyCharacter(String inputId, int whatCharacter) throws DataIOException {
		String sql = "SELECT no FROM project_game.user WHERE ID = ?";
		String sql2 = "UPDATE project_game.retainedcharacter SET have = 1 WHERE NO = ? AND WHO = ?";
		int inputIdNo = 0;
		try (Connection conn = ConnectionProvider.makeConnection();
				PreparedStatement stmt = conn.prepareStatement(sql);
				PreparedStatement stmt2 = conn.prepareStatement(sql2)) {
			stmt.setString(1, inputId);
			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					inputIdNo = rs.getInt(1);
					stmt2.setInt(1, inputIdNo);
					stmt2.setInt(2, whatCharacter);

					return stmt2.executeUpdate();
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DataIOException(e);
		}
		return -1;
	}

//가입시 기본캐릭터줌
	@Override
	public int standardCharacter(int lastNo) throws DataIOException {
		String sql = "INSERT INTO project_game.retainedcharacter (no, who, have) values (?, 1, 1);";
		String sql2 = "INSERT INTO project_game.retainedcharacter (no, who, have) values (?, 2, 0);";
		String sql3 = "INSERT INTO project_game.retainedcharacter (no, who, have) values (?, 3, 0);";
		String sql4 = "INSERT INTO project_game.retainedcharacter (no, who, have) values (?, 4, 0);";
		String sql5 = "INSERT INTO project_game.retainedcharacter (no, who, have) values (?, 5, 0);";
		try (Connection conn = ConnectionProvider.makeConnection();
				PreparedStatement stmt = conn.prepareStatement(sql);
				PreparedStatement stmt2 = conn.prepareStatement(sql2);
				PreparedStatement stmt3 = conn.prepareStatement(sql3);
				PreparedStatement stmt4 = conn.prepareStatement(sql4);
				PreparedStatement stmt5 = conn.prepareStatement(sql5)) {
			stmt.setInt(1, lastNo);
			stmt2.setInt(1, lastNo);
			stmt3.setInt(1, lastNo);
			stmt4.setInt(1, lastNo);
			stmt5.setInt(1, lastNo);

			stmt2.executeUpdate();
			stmt3.executeUpdate();
			stmt4.executeUpdate();
			stmt5.executeUpdate();

			return stmt.executeUpdate();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new DataIOException(e);
		}
	}

//가지고있는 캐릭터 확인
	@Override
	public int haveCharacter(int lastNo, int whatCharacter) throws DataIOException {
		String sql = "SELECT have FROM project_game.retainedcharacter where no = ? and who = ?";
		try (Connection conn = ConnectionProvider.makeConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setInt(1, lastNo);
			stmt.setInt(2, whatCharacter);

			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					return rs.getInt(1);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DataIOException(e);
		}
		return -1;
	}

//회원의pk확인
	@Override
	public int getMyNo(String inputId) throws DataIOException {
		String sql = "SELECT no FROM project_game.user where id = ?";
		try (Connection conn = ConnectionProvider.makeConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setString(1, inputId);

			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					return rs.getInt(1);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DataIOException(e);
		}
		return -1;
	}

	@Override
	public int choiceCharacte(int myNo, int whatCharacter) throws DataIOException {
		String sql = "UPDATE project_game.user SET gamecharacter = ? where no = ?";
		try (Connection conn = ConnectionProvider.makeConnection();	
				PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setInt(1, whatCharacter);
			stmt.setInt(2, myNo);
			return stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DataIOException(e);
		}
	}

	@Override
	public int findCharacte(String inputId) throws DataIOException {
		String sql = "SELECT gamecharacter FROM project_game.user WHERE ID = ?;";
		
		try (Connection conn = ConnectionProvider.makeConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setString(1, inputId);
			
			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					return rs.getInt(1);
				}
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DataIOException(e);
		}
		
		return 0;
	}

	@Override
	public int findLastRound(int myNo) throws DataIOException {
		String sql ="SELECT round FROM project_game.userscore WHERE no = ? order by round desc";
		
		try (Connection conn = ConnectionProvider.makeConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {
			
			stmt.setInt(1, myNo);
			
			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					return rs.getInt(1);
				}
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DataIOException(e);
		}
		
		
		return 0;
	}

	@Override
	public int stadardRound(int lastNo) throws DataIOException {
		String sql = "INSERT INTO project_game.userscore (no, round, score, roundMoney, roundCharacter) values (?, 0, 0, 0, 1)";

		try (Connection conn = ConnectionProvider.makeConnection();
				PreparedStatement stmt = conn.prepareStatement(sql);) {
			stmt.setInt(1, lastNo);

			return stmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
			throw new DataIOException(e);
		}
	}

	@Override
	public int saveScore(int myNo, int lastRound, int score, int money, int myCharacter) throws DataIOException {
		String sql = "INSERT INTO project_game.userscore (no, round, score, roundMoney, roundCharacter) values (?, ?, ?, ?, ?)";

		try (Connection conn = ConnectionProvider.makeConnection();
				PreparedStatement stmt = conn.prepareStatement(sql);) {
			stmt.setInt(1, myNo);
			stmt.setInt(2, lastRound + 1);
			stmt.setInt(3, score); 
			stmt.setInt(4, money);
			stmt.setInt(5, myCharacter);

			return stmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
			throw new DataIOException(e);
		}
	}

	@Override
	public int getMyCharacter(String inputId) throws DataIOException {
		String sql = "SELECT gamecharacter FROM project_game.user WHERE ID = ?";
		try (Connection conn = ConnectionProvider.makeConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {			
			stmt.setString(1, inputId);	
			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					return rs.getInt(1);
				}
			}		
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DataIOException(e);
		}	
		return 0;
	}

	

}
