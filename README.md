# aws-s3-rds
A Java program to upload data to Amazon AWS S3 and Amazon AWS RDS (MySQL) instance and measure performance.

###Steps for AWS S3:
1. Created an AWS account and registered for its Free Tier.
2. Downloaded PCI and University data.
3. Created an AWS S3 bucket with name `cse6331-bucket`.
4. Created an `ACCESS_KEY_ID` and `SECRET_ACCESS_KEY` for S3.
5. Using S3Uploader class, uploaded both the files placed in `data/` directory.

###Steps for AWS RDS MySQL:
1. Created an AWS RDS MySQL instance with default configuration (`cse6331.caf32xdtw87o.us-west-2.rds.amazonaws.com:3306/cse6331_db`).
2. Connected to AWS RDS MYSQL instance using local MySQL Workbench.
3. Created two tables in `cse6331_db`.
4. Using LOAD DATA query in `RDBUploader` class, loaded both the csv files into these two tables. 


###Steps for creating mapping between PCI and # of universities in a state
1. Executed an inner join query in `SIERMapper` class on both the tables to get the mapping between PCI and # Universities of a state.
