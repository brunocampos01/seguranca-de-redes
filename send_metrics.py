def send_metrics(type_metric, date_to_run, task_name, job_status, start_time, host, user, password):
    """
    Function to append metrics each job or module
    :param type_metric: jobs or modules
    :param date_to_run: type=date
    :param task_name: type=string, get in principal task
    :param job_status: type=string, get in principal task
    :param start_time: type=datetime, get in principal task
    :param host: get config file
    :param user: get config file
    :param password: get config file
    :return: None
    """
    logging.info("Inserting metrics in database ...")

    query_insert = metrics.prepare_metrics(type_metric, date_to_run, task_name, job_status, start_time)
    print(query_insert)

    cnx = manager_cnx(host, user, password)
    cursor = cnx.cursor(buffered=True)

    cursor.execute("USE `ETLMetrics`;")
    cursor.execute(query_insert)
    cnx.commit()  # Make sure data is committed to the database

    close_conn(connection=cnx, cursor=cursor)