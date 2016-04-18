package Controller;

public class InterpreteComandos{
	public int interpretarMovimiento(String comando){
		if ( comando.equals("") ) return -1;
		if ( comando.charAt(0) == 'W' || comando.charAt(0) == 'w' ) return 0;
		else if ( comando.charAt(0) == 'A' || comando.charAt(0) == 'a' ) return 1;
		else if ( comando.charAt(0) == 'S' || comando.charAt(0) == 's' ) return 2;
		else if ( comando.charAt(0) == 'D' || comando.charAt(0) == 'd' ) return 3;
		else if ( comando.charAt(0) == 'I' || comando.charAt(0) == 'i' ) return 4;
		else if ( comando.charAt(0) == 'J' || comando.charAt(0) == 'j' ) return 5;
		else if ( comando.charAt(0) == 'K' || comando.charAt(0) == 'k' ) return 6;
		else if ( comando.charAt(0) == 'L' || comando.charAt(0) == 'l' ) return 7;
		else return -1;
	}
	public boolean interpretarEspecial(String comando,String comandoIdeal){
		comando = comando.toUpperCase();
		return comando.equals(comandoIdeal);
	}
}