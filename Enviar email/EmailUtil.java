package fermoju.sisguias.util;

import java.text.SimpleDateFormat;
import java.util.Locale;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.cerberus.web.jsf.FacesUtil;

import fermoju.sisguias.domain.Serventia;
import fermoju.sisguias.domain.Usuario;

public class EmailUtil {

	private static final String urlSistema= "https://www2.tjce.jus.br/fermoju/";
	
	/**
	 * Retorna Url do servidor da aplica��o no formato: <br/>
	 * "http://www.host.com:8080"
	 */
	public static String getServerUrl() {
		FacesUtil
				.getFacesContext();
		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext()
				.getRequest();

		String scheme = request.getScheme();
		String serverName = request.getServerName();
		int port = request.getServerPort();
		
		return (scheme + "://" + serverName + ":" + port+"/fermoju/");
	}

	public static void emailConfirmacaoCadastro(Usuario usuario,
			Serventia serventia, String senha) throws EmailException {
		String subject = "[FERMOJU][SISGUIAS] - Email de Confirma��o de Cadastro";
		String emailConteudo = "Ol�, <b>%s</b> "
				+ "<br />"
				+ "<br />"
				+ "Recebemos de voc� um pedido de cadastramento para uso do Sistema Sisguias do Fermoju."
				+ "<br />"
				+ "Seu cadastro foi confirmado por um dos administradores do Fermoju no Tribunal de Justi�a do Estado do Cear�."
				+ "<br />"
				+ "<br />"
				+ "A partir de agora voc� tem acesso ao sistema, e para isso use os seguintes dados abaixo:"
				+ "<br />"
				+ "<br />"
				+ "Endere�o do Sistema: <a href='%s'><b>%s</b></a> "
				+ "<br />"
				+ "<br />"
				+ "Login: <b>%s</b> "
				+ "<br />"
				+ "Senha: <b>%s</b> "
				+ "<br />"
				+ "<br />"
				+ "� expressamente aconselhado pela equipe do Fermoju que logo no primeiro acesso voc� altere sua senha, "
				+ "que � de uso pessoal e intrasfer�vel."
				+ "<br />"
				+ "A senha enviada por esse email � gerada aleatoriamente, e nenhum dos funcion�rios do fermoju tem a acesso a mesma."
				+ "<br />"
				+ "<br />"
				+ "Agradecemos por sua aten��o, e obrigado por seguir as diretrizes de uso do sistema.";
		
		emailConteudo = String.format(emailConteudo, usuario.getNome(),
				urlSistema, urlSistema, usuario
				.getCpf(), senha);
		
		HtmlEmail email = getConfigEmail(usuario,emailConteudo,subject);
		email.send();
	}
	
	/**
	 * Configura o e-mail de cadastro do Servidor que obtem acesso ao fermoju 
	 * @param usuario
	 * @param serventia
	 * @param senha
	 * @throws EmailException
	 */
	public static void emailConfirmacaoCadastroServidor(Usuario usuario,
			Serventia serventia) throws EmailException {
		String subject = "[FERMOJU][SISGUIAS] - Email de Confirma��o de Cadastro";
		String emailConteudo = "Ol�, <b>%s</b> "
				+ "<br />"
				+ "<br />"
				+ "Recebemos de voc� um pedido de cadastramento para uso do Sistema Sisguias do Fermoju."
				+ "<br />"
				+ "Seu cadastro foi confirmado por um dos administradores do Fermoju no Tribunal de Justi�a do Estado do Cear�."
				+ "<br />"
				+ "<br />"
				+ "A partir de agora voc� tem acesso ao sistema, e para isso use os seguintes dados abaixo:"
				+ "<br />"
				+ "<br />"
				+ "Endere�o do Sistema: <a href='%s'><b>%s</b></a> "
				+ "<br />"
				+ "<br />"
				+ "Login: <b>%s</b> "
				+ "<br />"
				+ "Senha: <b>%s</b> "
				+ "<br />"
				+ "<br />"
				+ "Agradecemos por sua aten��o, e obrigado por seguir as diretrizes de uso do sistema.";
		
		emailConteudo = String.format(emailConteudo, usuario.getNome(),
				urlSistema, urlSistema, usuario
				.getMatricula(), "Sua SENHA de REDE");
		
		HtmlEmail email = getConfigEmail(usuario,emailConteudo,subject);
		email.send();
	}
	
	/**
	 * Obtem as configura��es para envio do e-mail
	 * @return
	 */
	private static HtmlEmail getConfigEmail(Usuario usuario,String msg ,String subject) throws EmailException{
		HtmlEmail email = new HtmlEmail();
		//email.setHostName("200.141.192.244"); // Servidor de e-mail com problema
		email.setHostName("192.168.10.38"); // Servidor de e-mail provis�rio
		email.addTo(usuario.getEmail(), usuario.getNome());
		email.setFrom("naoresponda@tjce.jus.br", "Fermoju [Sisguias]");
		email.setHtmlMsg(msg);
		email.setSubject(subject);
		return email;
	}
	
	/**
	 * Configura o e-mail do cadastro do serventu�rio no seu primeiro acesso
	 * @param usuario
	 * @param serventia
	 * @param senha
	 * @throws EmailException
	 */
	public static void emailConfirmCadastroServentuario(Usuario usuario,Serventia serventia, String senha) throws EmailException {
			StringBuffer emailConteudo = new StringBuffer();
			SimpleDateFormat dtCadastro = new SimpleDateFormat("dd 'de' MMMM 'de' yyyy", new Locale("pt","BR"));
			String subject = "[FERMOJU][SISGUIAS] - Email de Confirma��o de Cadastro de Serventu�rio";
			//Obtem o nome do usu�rio que est� confirmando o cadastro
			String usuarioHabilitacao = ((Usuario) FacesUtil.getSessionAttribute("usuario")).getNome();
			
			
			
	    	emailConteudo.append("  Email de Confirma��o de Cadastro de Serventu�rio");
	    	emailConteudo.append("<br />");
	    	emailConteudo.append("<br />");
	    	emailConteudo.append("Senhor(a) Tabeli�o(�) ");
	    	emailConteudo.append(" %s");
	    	emailConteudo.append("<br />");
	    	emailConteudo.append("<br />");
	    	emailConteudo.append("Comunicamos que foram recebidos os dados informados por V.Sa. no ");
	    	emailConteudo.append("formul�rio eletr�nico de  Cadastro de Usu�rio do novo sistema Sisguia Extrajudicial Online.");
	    	emailConteudo.append("<br />");
	    	emailConteudo.append("<br />");
	    	emailConteudo.append("Seu  cadastro foi confirmado pelo(a) funcion�rio(a) do Fermoju/TJCE, ");
	    	emailConteudo.append(usuarioHabilitacao);
	    	emailConteudo.append(" em ");
	    	emailConteudo.append(dtCadastro.format(usuario.getInicio()));
	    	emailConteudo.append(".");
	    	emailConteudo.append("<br />");
	    	emailConteudo.append("<br />");
	    	emailConteudo.append("O novo Sisguia Extrajudicial Online estar� acess�vel na p�gina do Tribunal de Justi�a " );
	    	emailConteudo.append("<a href='%s'><b>%s</b></a> ");
	    	emailConteudo.append(", a partir de 08 de dezembro de 2008.");
	    	emailConteudo.append("<br />");
	    	emailConteudo.append("Para ter acesso ao novo sistema o(a) Senhor(a) dever� informar os seguintes dados:");
	    	emailConteudo.append("<br />");
	    	emailConteudo.append("<br />");
	    	emailConteudo.append("Login: ");
	    	emailConteudo.append("<b>%s</b>");
	    	emailConteudo.append(" (n�mero do CPF)");
	    	emailConteudo.append("<br />");
	    	emailConteudo.append("Senha: ");
	    	emailConteudo.append("<b>%s</b>");
	    	emailConteudo.append("<br />");
	    	emailConteudo.append("<br />");
	    	emailConteudo.append("Embora a SENHA enviada por esse e-mail tenha sido gerada pelo sistema, aleatoriamente, "); 
	    	emailConteudo.append("e nenhum funcion�rio desse Tribunal de Justi�a tenha acesso a mesma, recomendamos que seja ");
	    	emailConteudo.append("alterada logo ap�s o 1� acesso, lembrando que ");
	    	emailConteudo.append("<b>a SENHA � de uso pessoal, sigilosa e intransfer�vel.</b>");
	    	emailConteudo.append("<br />");
	    	emailConteudo.append("<br />");
	    	emailConteudo.append("Esclarecemos que o acesso ao novo sistema dever� ser feito somente na Data de Implanta��o definida ");
	    	emailConteudo.append("para esse Cart�rio (Comarca) definido no calend�rio de Implanta��o publicado em Portaria desse Tribunal de Justi�a ");
	    	emailConteudo.append("e ap�s a emiss�o da  \"Guia de Fechamento\" do sistema Sisguia Offline ");
	    	emailConteudo.append("referente ao per�odo anterior a essa  data.");
	    	emailConteudo.append("<br />");
	    	emailConteudo.append("<br />");
	    	emailConteudo.append("Exemplo: ");
	    	emailConteudo.append("<br />");
	    	emailConteudo.append("O Cart�rio do 1� Of�cio do Registro Civil, Comarca de Caucaia Terceira Entrancia, s� poder� acessar o novo Sisguia Extrajudicial ");
	    	emailConteudo.append("Online a partir de 15/12/208 (Data de Implanta��o definida para os cart�rios dessa Comarca) e ap�s a emiss�o da ");
	    	emailConteudo.append("Guia de Recolhimento do Fermoju referente ao per�odo 08/12/2008 a 12/12/2008 (guia de fechamento do sistema  Sisguia Offline.");
	    	emailConteudo.append("<br />");
	    	emailConteudo.append("<br />");
	    	emailConteudo.append("Se o Valor do Fermoju acumulado at� a Data da Implanta��o desse Cart�rio n�o atingiu R$ 100,00 (cem reais), o Cart�rio dever� ");
	    	emailConteudo.append("fazer a atualiza��o do Sisguias off line para a vers�o nova 4.8, que permite a gera��o da \"Guia de Fechamento\"  com  valor ");
	    	emailConteudo.append("menor que R$100,00. O arquivo de atualiza��o estar� dispon�vel na p�gina do Fermoju (Internet e Intranet) op��o Sistemas/Download ");
	    	emailConteudo.append("(AtualizacaoSisguiaVers�o4.8.exe).");
	    	emailConteudo.append("<br />");
	    	emailConteudo.append("<br />");
	    	emailConteudo.append("Para maiores esclarecimentos, consultar informa��es sobre o novo sistema dispon�veis no site do Fermoju.");
	    	emailConteudo.append("<br />");
	    	emailConteudo.append("<br />");
	    	emailConteudo.append("<br />");
	    	emailConteudo.append("<br />");
	    	emailConteudo.append("Marcos Aur�lio Vieira Madeiro");
	    	emailConteudo.append("<br />");
	    	emailConteudo.append("Secret�rio Executivo do Fermoju");
	    	
	    	HtmlEmail email = EmailUtil.getConfigEmail(usuario
	    									,String.format(emailConteudo.toString(),serventia.getDescricao() + " / " + serventia.getId().getComarca().getNome(),
						    						urlSistema, urlSistema, usuario.getCpf(), senha)
						    				,subject);
			email.send();
	}
	
	/**
	 * E-mail de confirma��o da reiniciliza��o de senha
	 * @param usuario
	 * @param serventia
	 * @param senha
	 * @throws EmailException
	 */
	public static void emailReinicializacaoSenha(Usuario usuario,Serventia serventia, String senha) throws EmailException {
		StringBuffer emailConteudo = new StringBuffer();
		String subject = "[FERMOJU][SISGUIAS] - Reinicializa��o de senha Cadastro de Usu�rio";
		
		emailConteudo.append("[FERMOJU] - Reinicializa��o de senha Cadastro de Usu�rio");
    	emailConteudo.append("<br />");
    	emailConteudo.append("<br />");
    	emailConteudo.append("Senhor(a) Usu�rio(a) ");
    	emailConteudo.append("%s");
    	emailConteudo.append("<br />");
    	emailConteudo.append("<br />");
    	emailConteudo.append("Comunicamos que foi recebido um pedido de reinicializa��o da Senha de ");
    	emailConteudo.append("acesso ao novo sistema Sisguia Extrajudicial Online do Fermoju.");
    	emailConteudo.append("<br />");
    	emailConteudo.append("<br />");
    	emailConteudo.append("Sua nova senha �: ");
    	emailConteudo.append("<b>%s</b>");
    	emailConteudo.append("<br />");
    	emailConteudo.append("<br />");
    	emailConteudo.append("Embora a SENHA enviada por esse e-mail tenha sido gerada pelo sistema, aleatoriamente, e nenhum ");
    	emailConteudo.append("funcion�rio desse Tribunal de Justi�a tenha acesso a mesma, recomendamos que seja alterada logo ");
    	emailConteudo.append("ap�s o 1� acesso, lembrando que <b>a SENHA � de uso pessoal, sigilosa e intransfer�vel.</b>");
    	emailConteudo.append("<br />");
    	emailConteudo.append("<br />");
    	emailConteudo.append("Agradecemos por seguir as diretrizes de uso do sistema recomendadas pelo Fermoju.");
    	emailConteudo.append("<br />");
    	emailConteudo.append("<br />");
    	
    	HtmlEmail email = EmailUtil.getConfigEmail(usuario
    									,String.format(emailConteudo.toString(),usuario.getNome(),senha)
					    				,subject);
		email.send();
}
}
