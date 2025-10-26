-- CATEGORY TABLE
CREATE TABLE Category (
    id BIGINT IDENTITY(1,1) PRIMARY KEY,
    name NVARCHAR(255) NOT NULL
);

-- BOOK TABLE
CREATE TABLE Book (
    id BIGINT IDENTITY(1,1) PRIMARY KEY,
    title NVARCHAR(255) NOT NULL,
    author NVARCHAR(255) NOT NULL,
    year INT NOT NULL,
    imageUrl NVARCHAR(500),
    quantity INT NOT NULL DEFAULT 1,
    price FLOAT NOT NULL
);

-- BOOK_CATEGORY (Many-to-Many)
CREATE TABLE book_category (
    book_id BIGINT NOT NULL,
    category_id BIGINT NOT NULL,
    PRIMARY KEY (book_id, category_id),
    FOREIGN KEY (book_id) REFERENCES Book(id) ON DELETE CASCADE,
    FOREIGN KEY (category_id) REFERENCES Category(id) ON DELETE CASCADE
);

-- CUSTOMER TABLE
CREATE TABLE Customer (
    id BIGINT IDENTITY(1,1) PRIMARY KEY,
    name NVARCHAR(255) NOT NULL,
    email NVARCHAR(255) NOT NULL,
    phone NVARCHAR(50),
    address NVARCHAR(255)
);

-- ORDER TABLE
CREATE TABLE orders (
    id BIGINT IDENTITY(1,1) PRIMARY KEY,
    customer_id BIGINT,
    orderDate DATETIME2,
    total FLOAT,
    status NVARCHAR(50),
    FOREIGN KEY (customer_id) REFERENCES Customer(id)
);

-- ORDER ITEM TABLE
CREATE TABLE OrderItem (
    id BIGINT IDENTITY(1,1) PRIMARY KEY,
    order_id BIGINT,
    book_id BIGINT,
    quantity INT NOT NULL,
    price FLOAT NOT NULL,
    FOREIGN KEY (order_id) REFERENCES orders(id) ON DELETE CASCADE,
    FOREIGN KEY (book_id) REFERENCES Book(id)
);

-- PRE-POPULATE CATEGORIES
INSERT INTO Category (name) VALUES
('Fiction'),
('Non-Fiction'),
('Science'),
('Technology'),
('Children'),
('Comics'),
('History'),
('Biography'),
('Romance'),
('Mystery'),
('Fantasy'),
('Horror'),
('Self-Help'),
('Travel'),
('Health'),
('Education'); 