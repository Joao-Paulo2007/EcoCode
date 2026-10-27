
function abrirModal(id) {
    document.getElementById(id).classList.add('aberto');
}
function fecharModal(modal) {
    modal.classList.remove('aberto');
    const form = modal.querySelector('form');
    if (form) form.reset();
}
 
// botões do header (data-abrir="id-do-modal")
document.querySelectorAll('[data-abrir]').forEach(btn => {
    btn.addEventListener('click', () => abrirModal(btn.dataset.abrir));
});
 
// botão X e clique no fundo escuro
document.querySelectorAll('.modal').forEach(modal => {
    modal.querySelector('[data-fechar]').addEventListener('click', () => fecharModal(modal));
    modal.addEventListener('click', e => {
        if (e.target === modal) fecharModal(modal);
    });
});
 
// tecla ESC
document.addEventListener('keydown', e => {
    if (e.key === 'Escape') {
        document.querySelectorAll('.modal.aberto').forEach(fecharModal);
    }
});
 
// ---------- cadastro de resíduo ----------
document.getElementById('form-residuo').addEventListener('submit', e => {
    e.preventDefault();
    const dados = Object.fromEntries(new FormData(e.target));
    // descrição pode ser vazia, então não validamos ela
    console.log('Resíduo:', dados);   // TODO: enviar para o back-end / salvar
    fecharModal(document.getElementById('modal-residuo'));
});
 
// ---------- cadastro de pessoa ----------
const cpf = document.getElementById('cpf');
cpf.addEventListener('input', () => {            // máscara 000.000.000-00
    let v = cpf.value.replace(/\D/g, '').slice(0, 11);
    v = v.replace(/(\d{3})(\d)/, '$1.$2')
         .replace(/(\d{3})(\d)/, '$1.$2')
         .replace(/(\d{3})(\d{1,2})$/, '$1-$2');
    cpf.value = v;
});
 
const senha = document.getElementById('senha');
const confirmar = document.getElementById('confirmar-senha');
function checarSenhas() {
    confirmar.setCustomValidity(
        senha.value !== confirmar.value ? 'As senhas não coincidem' : ''
    );
}
senha.addEventListener('input', checarSenhas);
confirmar.addEventListener('input', checarSenhas);
 
document.getElementById('form-pessoa').addEventListener('submit', e => {
    e.preventDefault();
    const dados = Object.fromEntries(new FormData(e.target));
    delete dados.confirmarSenha;
    console.log('Pessoa:', dados);   // TODO: enviar para o back-end (nunca guarde senha em texto puro)
    fecharModal(document.getElementById('modal-pessoa'));
});