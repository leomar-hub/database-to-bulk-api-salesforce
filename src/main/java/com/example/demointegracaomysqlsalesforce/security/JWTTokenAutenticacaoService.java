package com.example.demointegracaomysqlsalesforce.security;

import com.example.demointegracaomysqlsalesforce.ApplicationContextLoad;
import com.example.demointegracaomysqlsalesforce.model.Usuario;
import com.example.demointegracaomysqlsalesforce.repository.UsuarioRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

@Service
@Component
public class JWTTokenAutenticacaoService {

	/*Tempo de de validade do Token (7200000 milissegundos = 2 horas)*/
	private static final long EXPIRATION_TIME = 7200000;

	/*Senha unica para compor a autenticação*/
	private static final String SECRET = "SenhaExtremamenteSecreta";

	/*Prefixo padrão de Token*/
	private static final String TOKEN_PREFIX = "Bearer";

	private static final String HEADER_STRING = "Authorization";

	/*Mensagem de não autorizado*/
	private static String notAuthorized = "User or password invalid!";

	/*Gerando token de autenticação e adicionando o cabeçalho e resposta Http*/
	public void addAuthentication(HttpServletResponse response, String username) throws IOException {

		/*Montagem do Token*/
		String JWT = Jwts.builder() /*Chama o gerador de Token*/
					   .setSubject(username) /*Adiciona o usuario*/
					   .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME)) /*Tempo de inspiração*/
					   .signWith(SignatureAlgorithm.HS512, SECRET).compact(); /*Compactação e algoritmos de geração de senha*/

		/*Junta o Token com o prefixo*/
		String token = TOKEN_PREFIX + " " + JWT;

		/*Adiciona no cabeçalho http*/
		response.addHeader(HEADER_STRING, token); /*Authorization: Bearer 564454vd4fgvd487f98n4df4gn5e4d4d5g45df4g8h*/

		/*Liberando resposta para porta diferente do projeto angular --CORS--*/
		response.addHeader("Access-Control-Allow-Origin", "*");

		/*Escreve token como resposta no corpo http*/
		response.getWriter().write("{\"Authorization\": \""+token+"\"}" + "\n\n\n## Adicione esse token no header das request ##" );

	}

	/*Retorna o usuario validado com token ou caso não seja valido retorna null*/
	public Authentication getAuthentication(HttpServletRequest request) {

		/*Pega o token enviado no cabeçalho http*/
		String token = request.getHeader(HEADER_STRING);

		if (token != null) {
			/*Faz a validação do token do usuario na validação*/
			String user = Jwts.parser().setSigningKey(SECRET) /*Bearer 564454vd4fgvd487f98n4df4gn5e4d4d5g45df4g8h*/
					          .parseClaimsJws(token.replace(TOKEN_PREFIX, ""))/* 564454vd4fgvd487f98n4df4gn5e4d4d5g45df4g8h*/
					          .getBody().getSubject();
			if(user != null) {

				Usuario usuario = ApplicationContextLoad.getApplicationContext()
						.getBean(UsuarioRepository.class).findUserByLogin(user);

				if(usuario != null) {

					return new UsernamePasswordAuthenticationToken(
							usuario.getLogin(),
							usuario.getSenha(),
							usuario.getAuthorities());
				}

			}

		}
		return null; /*Não autorizado*/
	}
}
