from datetime import datetime, date


def prepare_metrics(type_metric, date_to_run, task_name, job_status, start_time):
    """
    :param type_metric:
    :param date_to_run:
    :param task_name:
    :param job_status:
    :param start_time:
    :return:
    """
    task_name = str(task_name)
    end_time = datetime.now()
    execution_time = end_time - start_time
    sla_success = True
    today = date.today()

    if type_metric == "jobs":
        query_insert = "INSERT INTO jobs " \
                       "(executionDate, jobName, statusJob, startTime, endTime, executionTime) " \
                       "VALUES ('%s', '%s', '%s', '%s', '%s', '%s');" \
                       % (date_to_run,
                          task_name,
                          job_status,
                          start_time,
                          end_time,
                          execution_time)
    else:
        if end_time >= datetime(today.year, today.month, today.day, hour=9, minute=0, second=0):
            sla_success = False

        query_insert = "INSERT INTO modules " \
                       "(executionDate, slaSuccess, moduleName, statusModule, startTime, endTime, executionTime) " \
                       "VALUES ('%s', '%s', '%s', '%s', '%s', '%s', '%s');" \
                       % (date_to_run,
                          sla_success,
                          task_name,
                          job_status,
                          start_time,
                          end_time,
                          execution_time)
    return query_insert
