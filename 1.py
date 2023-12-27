from selenium import webdriver
from selenium.webdriver.chrome.options import Options
from selenium.webdriver.common.keys import Keys
from selenium.webdriver.common.by import By
from webdriver_manager.chrome import ChromeDriverManager

chrome_options = Options()
chrome_options.add_experimental_option("detach", True)

driver = webdriver.Chrome(ChromeDriverManager.install())

driver.get('https://search.naver.com/search.naver?where=view&sm=tab_jum&query=%EC%97%AD%EC%82%BC+%EB%A7%9B%EC%A7%91')

driver.quit()