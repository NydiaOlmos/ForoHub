alter table topicos add unique (titulo);
alter table topicos add unique (mensaje);
alter table respuestas add unique (mensaje);
alter table cursos add unique (nombre);