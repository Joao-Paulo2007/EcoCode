package Domain;

import Util.ValidacoesUsuario;
import Util.ValidadorCpf;
import Util.ValidarEmail;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Usuario {
    private String nomeCompleto;
    private String cpf;
    private String email;
    private String senhaCriptografada;
    private CargoUsuario cargoUsuario;
    private LocalDate dataNascimento;

    // Formatador estático para o padrão brasileiro DD/MM/AAAA
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final ValidacoesUsuario validar = new ValidacoesUsuario();

    public Usuario(String nomeCompleto, String cpf, String email, String senhaCriptografada, CargoUsuario cargoUsuario, String dataNascimento) {
        // Validações de Obrigatoriedade (Fail-Fast)
        validar.validarUsuario(nomeCompleto, email, cpf, cargoUsuario);

        // Validação e Atribuição da Data de Nascimento
        try {
            this.dataNascimento = LocalDate.parse(dataNascimento, FORMATTER);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Formato de data inválido! Use o padrão DD/MM/AAAA.");
        }

        // Atribuição dos demais campos
        this.nomeCompleto = nomeCompleto;
        this.cpf = ValidadorCpf.limpar(cpf);
        this.email = email;
        this.senhaCriptografada = senhaCriptografada;
        this.cargoUsuario = cargoUsuario;
    }

    public String getNomeCompleto() {
        return nomeCompleto;
    }

    public String getEmail() {
        return email;
    }

    public String getSenhaCriptografada() {
        return senhaCriptografada;
    }

    public CargoUsuario getCargoUsuario() {
        return cargoUsuario;
    }

    public String getDataNascimento() {
        return dataNascimento.format(FORMATTER);   // "14/05/2006"
    }

    public String getCpf() {
        return cpf;
    }

    public void setNomeCompleto(String nomeCompleto) {
        this.nomeCompleto = nomeCompleto;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setSenhaCriptografada(String senhaCriptografada) {
        this.senhaCriptografada = senhaCriptografada;
    }
}
