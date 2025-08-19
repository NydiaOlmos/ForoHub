create table cursos (
    id bigint not null GENERATED ALWAYS AS IDENTITY,
    nombre varchar(100) not null unique,
    categoria varchar(100) not null,

    primary key(id)
);

create table usuarios (
    id bigint not null GENERATED ALWAYS AS IDENTITY,
    nombre varchar(100) not null,
    email varchar(100) not null unique,
    contrasena varchar(255) not null,
    activo boolean not null,

    primary key(id)
);

create table topicos (
    id bigint not null GENERATED ALWAYS AS IDENTITY,
    titulo varchar(100) not null unique,
    mensaje text not null,
    fecha_creacion TIMESTAMP not null,
    status BOOLEAN not null,
    autor bigint not null,
    curso bigint not null,

    primary key(id),
    constraint fk_consultas_usuario_id foreign key(autor) references usuarios(id),
    constraint fk_consultas_curso_id foreign key(curso) references cursos(id)
);

create table respuestas (
    id bigint not null GENERATED ALWAYS AS IDENTITY,
    mensaje text not null,
    topico bigint not null,
    fecha_creacion TIMESTAMP not null,
    autor bigint not null,
    solucion BOOLEAN not null,

    primary key(id),
    constraint fk_consultas_usuario_id foreign key(autor) references usuarios(id),
    constraint fk_consultas_topico_id foreign key(topico) references topicos(id)
);

create table roles (
    id varchar(20) not null,
    nombre varchar(100) not null,

    primary key(id)
);

create table usuario_rol (
    id bigint not null GENERATED ALWAYS AS IDENTITY,
    id_usuario bigint not null,
    id_rol varchar(20) not null,

    primary key(id),
    constraint fk_consultas_usuario_id foreign key(id_usuario) references usuarios(id),
    constraint fk_consultas_rol_id foreign key(id_rol) references roles(id)
);
