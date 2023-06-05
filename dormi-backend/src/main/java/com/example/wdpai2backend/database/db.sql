CREATE IF NOT EXISTS DATABASE DORMI_APP;

-- Connect to the database
\c DORMI_APP;

-- Create a new table
CREATE TABLE IF NOT EXISTS  authority (
                        id_auth SERIAL PRIMARY KEY,
                       authority VARCHAR(255) NOT NULL

);

-- Insert some sample data into the table
INSERT INTO authority (id_auth, authority) VALUES
                                    (1, 'user'),
                                    (2, 'admin');

create table if not exists dormitory(
                                        id_dorm SERIAL PRIMARY KEY,
                                        dorm_name VARCHAR(255)
    );

insert into dormitory(id_dorm, dorm_name) values
                                              (1, 'Dorm 1'),
                                              (2, 'Dorm 2'),
                                              (3, 'Dorm 3');

insert into device(name_device, work, number, id_dorm)
                                            values
                                            ('washer', true, 1, 1),
                                            ('iron', true, 1, 1),
                                            ('washer', true, 2, 1),
                                            ('cos', true, 1, 2);


CREATE OR REPLACE FUNCTION trigger_reservation()
    RETURNS TRIGGER AS $$
BEGIN
    IF TG_OP = 'INSERT' AND EXISTS (
            SELECT 1 FROM reservations
            WHERE id_device = NEW.id_device AND (
                    (reservations.start_date <= NEW.start_date AND NEW.start_date < reservations.end_date) OR
                    (reservations.start_date < NEW.end_date AND NEW.end_date <= reservations.end_date)
                )
        ) THEN
        RAISE EXCEPTION 'Device is already reserved during this time';
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;
drop trigger if exists reservation on reservations;
CREATE TRIGGER reservation
    BEFORE INSERT ON reservations
    FOR EACH ROW
EXECUTE FUNCTION trigger_reservation();