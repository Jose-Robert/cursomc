package br.com.cursomc;


import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import br.com.cursomc.domain.Categoria;
import br.com.cursomc.domain.Cidade;
import br.com.cursomc.domain.Cliente;
import br.com.cursomc.domain.Endereco;
import br.com.cursomc.domain.Estado;
import br.com.cursomc.domain.Pagamento;
import br.com.cursomc.domain.PagamentoComBoleto;
import br.com.cursomc.domain.PagamentoComCartao;
import br.com.cursomc.domain.Pedido;
import br.com.cursomc.domain.Produto;
import br.com.cursomc.domain.enums.EstadoPagamento;
import br.com.cursomc.domain.enums.TipoCliente;
import br.com.cursomc.repositories.CategoriaRepository;
import br.com.cursomc.repositories.CidadeRepository;
import br.com.cursomc.repositories.ClienteRepository;
import br.com.cursomc.repositories.EnderecoRepository;
import br.com.cursomc.repositories.EstadoRepository;
import br.com.cursomc.repositories.PagamentoRepository;
import br.com.cursomc.repositories.PedidoRepository;
import br.com.cursomc.repositories.ProdutoRepository;

	@SpringBootApplication
	public class CursomcApplication implements CommandLineRunner {
		
		@Autowired
		private ProdutoRepository produtoRepository;
		@Autowired
		private CategoriaRepository categoriaRepository;
		@Autowired
		private CidadeRepository cidadeRepository;
		@Autowired
		private EstadoRepository estadoRepository;
		@Autowired
		private ClienteRepository clienteRepository; 
		@Autowired
		private EnderecoRepository enderecoRepository;
		@Autowired
		private PedidoRepository pedidoRepository;
		@Autowired
		private PagamentoRepository pagamentoRepository;
		
		public static void main(String[] args) {
			SpringApplication.run(CursomcApplication.class, args);
		}
	
		@Override
		public void run(String... args) throws Exception {
	
			Categoria cat1 = new Categoria(null, "Informatica");
			Categoria cat2 = new Categoria(null, "Escritorio");
			
			Produto p1 = new Produto(null,"Computador", 2000.00);
			Produto p2 = new Produto(null,"Impressora", 800.00);
			Produto p3 = new Produto(null,"Mouse", 80.00);
			
			p1.getCategorias().addAll(Arrays.asList(cat1));
			p2.getCategorias().addAll(Arrays.asList(cat1,cat2));
			p3.getCategorias().addAll(Arrays.asList(cat1));			
	
			categoriaRepository.save(Arrays.asList(cat1,cat2 ));
			produtoRepository.save(Arrays.asList(p1,p2,p3));
			
			Estado est1 = new Estado(null, "Minas Gerais");
			Estado est2 = new Estado(null, "São Paulo");
			
			Cidade c1 = new Cidade(null,"Uberlândia", est1);
			Cidade c2 = new Cidade(null,"São Paulo", est2);
			Cidade c3 = new Cidade(null,"Campinas", est2);
			
			est1.getCidades().addAll(Arrays.asList(c1));
			est2.getCidades().addAll(Arrays.asList(c2,c3));
			
			estadoRepository.save(Arrays.asList(est1,est2));
			cidadeRepository.save(Arrays.asList(c1,c2,c3));
			
			Cliente cli1 = new Cliente(null, "Maria Silva", "maria@gmail.com", "36378912377", TipoCliente.PESSOAFISICA);
	
			cli1.getTelefones().addAll(Arrays.asList("27363323", "93838393"));
			
			Endereco e1 = new Endereco(null, "Rua Flores", "300", "Apto 203","Jardim", "38220834", cli1, c1 );
			Endereco e2 = new Endereco(null, "Avenida Matos", "105", "Sala 800", "Centro", "38777012",cli1, c2);
			
			cli1.getEnderecos().addAll(Arrays.asList(e1,e2));
			
			clienteRepository.save(Arrays.asList(cli1));
			enderecoRepository.save(Arrays.asList(e1,e2));
			
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm");
			
			Pedido ped1 = new Pedido(null, sdf.parse("30/09/2017 10:32"), cli1, e1);
			Pedido ped2 = new Pedido(null, sdf.parse("10/10/2017 19:35"), cli1, e2);
			
			Pagamento pagto1 = new PagamentoComCartao(null, EstadoPagamento.QUITADO, ped1, 6);
			ped1.setPagamento(pagto1);
			
			Pagamento pagto2 = new PagamentoComBoleto(null, EstadoPagamento.PENDENTE, ped2, 
			sdf.parse("20/10/2017 00:00"), null);
			ped2.setPagamento(pagto2);
			
			pedidoRepository.save(Arrays.asList(ped1, ped2));
			pagamentoRepository.save(Arrays.asList(pagto1, pagto2));
		}
	
}
