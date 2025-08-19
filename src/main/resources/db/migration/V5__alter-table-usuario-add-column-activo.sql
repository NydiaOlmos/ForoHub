alter table usuarios add column activo boolean;
update usuarios set activo = true;
alter table usuarios alter column activo set not null;