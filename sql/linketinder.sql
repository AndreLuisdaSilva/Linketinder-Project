CREATE TABLE companies (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    description TEXT,
    cnpj VARCHAR(255) UNIQUE NOT NULL,
    country VARCHAR(50),
    cep VARCHAR(255),
    password VARCHAR(255) NOT NULL,
    state VARCHAR(2),
    skills TEXT,
    register_date DATE DEFAULT CURRENT_DATE
);

CREATE TABLE candidates (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    description TEXT,
    education TEXT,
    experience TEXT,
    cep VARCHAR(255),
    age INTEGER,
    password VARCHAR(255) NOT NULL,
    country VARCHAR(50),
    state VARCHAR(2),
    skills TEXT
);

CREATE TABLE vacancies (
    id SERIAL PRIMARY KEY,
    title VARCHAR(100) NOT NULL,
    description TEXT,
    company_id INTEGER REFERENCES companies(id),
    location VARCHAR(100),
    publication_date DATE DEFAULT CURRENT_DATE
);

CREATE TABLE skills (
    id SERIAL PRIMARY KEY,
    name VARCHAR(50) UNIQUE NOT NULL
);

CREATE TABLE candidate_skills (
    candidate_id INTEGER REFERENCES candidates(id),
    skill_id INTEGER REFERENCES skills(id),
    level VARCHAR(50),
    PRIMARY KEY (candidate_id, skill_id)
);

CREATE TABLE vacancy_skills (
    vacancy_id INTEGER REFERENCES vacancies(id),
    skill_id INTEGER REFERENCES skills(id),
    PRIMARY KEY (vacancy_id, skill_id)
);

CREATE TABLE company_candidates (
    company_id INTEGER REFERENCES companies(id),
    candidate_id INTEGER REFERENCES candidates(id),
    liked BOOLEAN DEFAULT false,
    PRIMARY KEY (company_id, candidate_id)
);

CREATE TABLE candidate_vacancies (
    candidate_id INTEGER REFERENCES candidates(id),
    vacancy_id INTEGER REFERENCES vacancies(id),
    liked BOOLEAN DEFAULT false,
    PRIMARY KEY (candidate_id, vacancy_id)
);

CREATE TABLE candidate_companies (
    candidate_id INTEGER REFERENCES candidates(id),
    company_id INTEGER REFERENCES companies(id),
    liked BOOLEAN DEFAULT false,
    PRIMARY KEY (candidate_id, company_id)
);

INSERT INTO companies (name, cnpj, email, description, country, cep, password, state, skills) VALUES
('Tech Solutions', '11.111.111/0001-11', 'contato@techsolutions.com', 'Empresa de desenvolvimento de software', 'Brasil', '11111-111', 'senha123', 'SP', 'Node.js,React,Java,Python'),
('Inova Corp', '22.222.222/0001-22', 'contato@inovacorp.com', 'Consultoria em TI', 'Brasil', '22222-222', 'senha456', 'RJ', 'Java,AWS,Docker,Kubernetes'),
('Data Minds', '33.333.333/0001-33', 'contato@dataminds.com', 'Análise de dados e BI', 'Brasil', '33333-333', 'senha789', 'MG', 'Python,SQL,PowerBI,Tableau'),
('Creative Designs', '44.444.444/0001-44', 'contato@creativedesigns.com', 'Agência de design e marketing', 'Brasil', '44444-444', 'senhaabc', 'BA', 'Adobe Creative Suite,Figma,UX Research'),
('Project Builders', '55.555.555/0001-55', 'contato@projectbuilders.com', 'Gerenciamento de projetos', 'Brasil', '55555-555', 'senhadef', 'PR', 'Agile,PMP,Jira,Confluence');

INSERT INTO candidates (name, email, age, country, cep, description, education, experience, password, state, skills) VALUES
('João Silva', 'joao.silva@email.com', 32, 'Brasil', '12345-678', 'Desenvolvedor Full Stack', 'Ciência da Computação', '5 anos de experiência com Node.js e React.', 'senha123', 'SP', 'Node.js,React,SQL'),
('Maria Santos', 'maria.santos@email.com', 29, 'Brasil', '23456-789', 'Engenheira de Software', 'Engenharia de Software', 'Especialista em Java e sistemas distribuídos.', 'senha456', 'RJ', 'Java,Docker,AWS'),
('Pedro Oliveira', 'pedro.oliveira@email.com', 35, 'Brasil', '34567-890', 'Cientista de Dados', 'Estatística', 'Forte conhecimento em Python, R e Machine Learning.', 'senha789', 'MG', 'Python,R,Machine Learning,SQL'),
('Ana Souza', 'ana.souza@email.com', 27, 'Brasil', '45678-901', 'Designer UX/UI', 'Design Gráfico', 'Criação de interfaces intuitivas e modernas.', 'senhaabc', 'BA', 'Figma,Sketch,Adobe XD'),
('Carlos Ferreira', 'carlos.ferreira@email.com', 30, 'Brasil', '56789-012', 'Gerente de Projetos', 'Administração', 'Certificação PMP e experiência com metodologias ágeis.', 'senhadef', 'PR', 'Scrum,Kanban,Jira');


INSERT INTO skills (name) VALUES ('Python'), ('Java'), ('JavaScript'), ('SQL'), ('React'), ('Angular'), ('Node.js'), ('Docker'), ('AWS');

INSERT INTO vacancies (title, description, location, company_id) VALUES
('Desenvolvedor Backend', 'Desenvolvimento de APIs com Node.js', 'Remoto', 1),
('Engenheiro de Dados', 'Criação de pipelines de dados com Python e SQL', 'São Paulo', 3),
('Desenvolvedor Frontend', 'Desenvolvimento de interfaces com React', 'Remoto', 1);

INSERT INTO candidate_skills (candidate_id, skill_id, level) VALUES
(1, 1, 'Avançado'), (1, 3, 'Avançado'), (1, 7, 'Avançado'),
(2, 2, 'Avançado'), (2, 8, 'Intermediário'),
(3, 1, 'Avançado'), (3, 4, 'Avançado'),
(4, 5, 'Intermediário'), (4, 6, 'Básico');

INSERT INTO vacancy_skills (vacancy_id, skill_id) VALUES
(1, 3), (1, 7),
(2, 1), (2, 4),
(3, 5);

INSERT INTO company_candidates(company_id, candidate_id, liked) VALUES
(1, 1, true), 
(3, 3, true);

INSERT INTO candidate_vacancies(candidate_id, vacancy_id, liked) VALUES
(1, 1, true), 
(2, 1, true), 
(3, 2, true);

INSERT INTO candidate_companies(candidate_id, company_id, liked) VALUES
(1, 1, true);
