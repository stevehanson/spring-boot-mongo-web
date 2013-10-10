/*
 * Copyright 2012-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.mongodb.MongoClient;

@EnableAutoConfiguration
@ComponentScan
@Controller
public class Application {

	@Autowired
	MongoOperations mongo;

	
	@RequestMapping(value="/", method=RequestMethod.GET)
	public String loadHomePage(Model m) {
		m.addAttribute("person", new Person());
		m.addAttribute("people", mongo.findAll(Person.class));
		return "formPage";
	}
	
	@RequestMapping(value="/", method=RequestMethod.POST)
	public String submitForm(Person person, Model m) {
		System.out.println(person);
		mongo.save(person);
		return "redirect:/";
	}

	public static void main(String[] args) throws Exception {
		SpringApplication.run(Application.class, args);
	}
	
	@Bean
	public MongoDbFactory mongoDbFactory() throws Exception {
		return new SimpleMongoDbFactory(new MongoClient(), "springmongo");
	}
 
	@Bean
	public MongoTemplate mongoTemplate() throws Exception {
		MongoTemplate mongoTemplate = new MongoTemplate(mongoDbFactory());
		return mongoTemplate;
 
	}

}
