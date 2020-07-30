package com.example.demo;

import com.example.demo.netty.NettyServer;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.net.InetSocketAddress;

@MapperScan("com.example.demo.dao")
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@EnableScheduling
public class DemoApplication implements CommandLineRunner {
	@Value("${netty.port}")
	private int port;

	@Value("${netty.url}")
	private String url;

	@Autowired
	private NettyServer server;

	@Override
	public void run(String... args) throws Exception {
		InetSocketAddress address = new InetSocketAddress(url,port);
		System.out.println("run  .... . ... "+url);
		server.start(address);
	}

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

}
