CREATE TABLE "User " (
    id SERIAL PRIMARY KEY,
    firstname VARCHAR(255) NOT NULL,
    lastname VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    type VARCHAR(50) NOT NULL
);


CREATE TABLE salle (
    id_salle SERIAL PRIMARY KEY,
    nom_salle VARCHAR(255) NOT NULL,
    capacite INT NOT NULL
);

CREATE TABLE terrain (
    id_terrain SERIAL PRIMARY KEY,
    nom_terrain VARCHAR(255) NOT NULL,
    type VARCHAR(50) NOT NULL
);

CREATE TABLE terrain (
    id_terrain SERIAL PRIMARY KEY,
    nom_terrain VARCHAR(255) NOT NULL,
    type VARCHAR(50) NOT NULL
);

CREATE TABLE reservation (
    id_reservation SERIAL PRIMARY KEY,
    id_user INT NOT NULL,
    id_event INT NOT NULL,
    id_salle INT NOT NULL,
    id_terrain INT NOT NULL,
    date_reservation DATE NOT NULL,
    FOREIGN KEY (id_user) REFERENCES "User " (id),
    FOREIGN KEY (id_event) REFERENCES event (id),  -- Assuming you have an event table
    FOREIGN KEY (id_salle) REFERENCES salle (id_salle),
    FOREIGN KEY (id_terrain) REFERENCES terrain (id_terrain)
);
