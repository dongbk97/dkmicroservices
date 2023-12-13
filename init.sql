CREATE EXTENSION IF NOT EXISTS dblink;

DO $$
BEGIN
PERFORM dblink_exec('', 'CREATE DATABASE employee_db');
EXCEPTION WHEN duplicate_database THEN RAISE NOTICE '%, skipping', SQLERRM USING ERRCODE = SQLSTATE;
END
$$;

DO $$
BEGIN
PERFORM dblink_exec('', 'CREATE DATABASE department_db');
EXCEPTION WHEN duplicate_database THEN RAISE NOTICE '%, skipping', SQLERRM USING ERRCODE = SQLSTATE;
END
$$;

DO $$
BEGIN
PERFORM dblink_exec('', 'CREATE DATABASE location_db');
EXCEPTION WHEN duplicate_database THEN RAISE NOTICE '%, skipping', SQLERRM USING ERRCODE = SQLSTATE;
END
$$;

DO $$
BEGIN
PERFORM dblink_exec('', 'CREATE DATABASE notification_db');
EXCEPTION WHEN duplicate_database THEN RAISE NOTICE '%, skipping', SQLERRM USING ERRCODE = SQLSTATE;
END
$$;

DO $$
BEGIN
PERFORM dblink_exec('', 'CREATE DATABASE recruitment_db');
EXCEPTION WHEN duplicate_database THEN RAISE NOTICE '%, skipping', SQLERRM USING ERRCODE = SQLSTATE;
END
$$;

DO $$
BEGIN
PERFORM dblink_exec('', 'CREATE DATABASE attendance_db');
EXCEPTION WHEN duplicate_database THEN RAISE NOTICE '%, skipping', SQLERRM USING ERRCODE = SQLSTATE;
END
$$;

-- Connect to department_db and create table tbl_department
\c department_db;

-- Create table tbl_department
CREATE TABLE IF NOT EXISTS tbl_department (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) UNIQUE NOT NULL
);

-- Insert values into tbl_department
INSERT INTO tbl_department (name) VALUES
    ('Executive Leadership'),
    ('Engineering and Development'),
    ('Data and Analytics'),
    ('IT and Security'),
    ('Sales and Marketing'),
    ('Research and Development'),
    ('Operations and Support'),
    ('HR Department');

-- Connect to employee_db and create table tbl_position
\c employee_db;

-- Create table tbl_position
CREATE TABLE IF NOT EXISTS tbl_position (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) UNIQUE NOT NULL
);

-- Insert values into tbl_position
INSERT INTO tbl_position (name) VALUES
    ('CEO'),
    ('CTO'),
    ('Project Manager'),
    ('Technical Leader'),
    ('Software Engineer'),
    ('QA/QC'),
    ('Tester'),
    ('BA'),
    ('IT Helpdesk'),
    ('HR');