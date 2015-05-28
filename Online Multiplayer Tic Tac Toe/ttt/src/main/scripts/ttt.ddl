
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

    create sequence hibernate_sequence;
