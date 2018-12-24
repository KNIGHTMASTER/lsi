package com.tripoin.lsi.core.formula;

import com.tripoin.lsi.core.data.dto.request.RequestFormulaInterviewCompletion;
import com.wissensalt.util.common.formula.AFormulaCalculator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;

/**
 * Created on 9/15/18.
 *
 * @author <a href="mailto:fauzi.knightmaster.achmad@gmail.com">Achmad Fauzi</a>
 */
@Service
public class FormulaInterviewCompletion extends AFormulaCalculator<RequestFormulaInterviewCompletion, Double> {

    private static final Logger LOGGER = LoggerFactory.getLogger(FormulaInterviewCompletion.class);

    @Override
    public void calculate() {
        if (getParam().getNumberOfRespondent() != null && getParam().getNumberOfCompletedInterview() != null) {
            if (getParam().getNumberOfRespondent() != 0) {
                DecimalFormat decimalFormat = new DecimalFormat("#.00");
                setResult(
                        Double.valueOf(decimalFormat.format((Double.valueOf(getParam().getNumberOfCompletedInterview()) / Double.valueOf(getParam().getNumberOfRespondent())) * 100))
                );
            }else {
                setResult(0D);
                LOGGER.error("Number of Respondent is 0");
            }
        }else {
            setResult(0D);
            LOGGER.error("Error Formula Interview Completion Number of Respondent or Number of Success Interview is Empty");
        }
    }
}
