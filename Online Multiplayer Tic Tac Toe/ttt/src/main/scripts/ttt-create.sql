
    create table game_users (
        userId int4 not null,
        email varchar(255),
        password varchar(255) not null,
        username varchar(255) not null,
        primary key (userId)
    );

    create table games (
        gameId int4 not null,
        againstAI boolean not null,
        endTime timestamp,
        gameState int4,
        saveTime timestamp,
        startTime timestamp,
        player1 int4,
        player2 int4,
        primary key (gameId)
    );

    create table games_played (
        game_users_userId int4 not null,
        playedGames_gameId int4 not null
    );

    create table games_saved (
        game_users_userId int4 not null,
        savedGames_gameId int4 not null
    );

    create table moves (
        game_id int4 not null,
        board varchar(255)
    );

    create table users (
        id int4 not null,
        enabled boolean not null,
        password varchar(255),
        username varchar(255),
        primary key (id)
    );
    
    create table authorities (
    username    varchar(255) not null references users(username),
    authority   varchar(255) default 'ROLE_USER'
	);
	
	insert into authorities values('cysun', 'ROLE_USER');
	
	CREATE FUNCTION insertdata() returns trigger as $testref$
    BEGIN
    IF (TG_OP='INSERT') THEN
      INSERT INTO authorities (username) values (NEW.username);
      return NEW;
    END IF;
    END;
    $testref$ LANGUAGE plpgsql;
    CREATE TRIGGER testref AFTER INSERT ON game_users
  	FOR EACH ROW 
  	EXECUTE PROCEDURE insertdata();

    alter table game_users 
        add constraint UK_e2nuh23mwrv4qifrjys6n8rga unique (email);

    alter table game_users 
        add constraint UK_8w40ic578mbdmfgssaue355tj unique (username);

    alter table games_played 
        add constraint UK_2gucp10m8pmtm7la6tx3i7nvu unique (playedGames_gameId);

    alter table games_saved 
        add constraint UK_680m1yj38jx96ae2uasqk1qex unique (savedGames_gameId);

    alter table games 
        add constraint FK_i7yk0yshppqtddgrc7gbd9hke 
        foreign key (player1) 
        references game_users;

    alter table games 
        add constraint FK_2eabwf5b3wtie9xcua0fcd2x3 
        foreign key (player2) 
        references game_users;

    alter table games_played 
        add constraint FK_2gucp10m8pmtm7la6tx3i7nvu 
        foreign key (playedGames_gameId) 
        references games;

    alter table games_played 
        add constraint FK_bik0pqnqimaimqk9ik2lb3dn7 
        foreign key (game_users_userId) 
        references game_users;

    alter table games_saved 
        add constraint FK_680m1yj38jx96ae2uasqk1qex 
        foreign key (savedGames_gameId) 
        references games;

    alter table games_saved 
        add constraint FK_lc1qj3gf6dv25utb6i6ttoe6n 
        foreign key (game_users_userId) 
        references game_users;

    alter table moves 
        add constraint FK_n13u4it4ev6u7eeiq1ltifti0 
        foreign key (game_id) 
        references games;

    create sequence hibernate_sequence START WITH 100;



insert into game_users values (1,'cysun@localhost.localdomain', 'abcd', 'cysun');


insert into games values (1, true, NOW(), 1, NOW(), NOW(), 1, null);

insert into games values (2, true, NOW(), 3, NOW(), NOW(), 1, null);

insert into games values (3, true, null, 0, NOW(), NOW(), 1, null);

insert into games_played values (1, 1);

insert into games_played values (1, 2);

insert into games_saved values (1, 3);

insert into moves values (1, '0:X');

insert into moves values (1, '1:O');

insert into moves values (1, '2:');

insert into moves values (1, '3:X');

insert into moves values (1, '4:X');

insert into moves values (1, '5:O');

insert into moves values (1, '6:O');

insert into moves values (1, '7:');

insert into moves values (1, '8:X');



insert into moves values (2, '0:X');

insert into moves values (2, '1:');

insert into moves values (2, '2:O');

insert into moves values (2, '3:');

insert into moves values (2, '4:X');

insert into moves values (2, '5:O');

insert into moves values (2, '6:X');

insert into moves values (2, '7:');

insert into moves values (2, '8:O');


insert into moves values (3, '0:X');

insert into moves values (3, '1:');

insert into moves values (3, '2:');

insert into moves values (3, '3:');

insert into moves values (3, '4:O');

insert into moves values (3, '5:');

insert into moves values (3, '6:');

insert into moves values (3, '7:');

insert into moves values (3, '8:');